package com.godwin.ui;

import android.text.TextUtils;
import com.godwin.model.DDatabase;
import com.godwin.model.DTable;
import com.godwin.model.IBaseModel;
import com.godwin.network.ClientSocket;
import com.godwin.network.communication.DataCommunicationListener;
import com.godwin.network.communication.DataObserver;
import com.godwin.ui.autocomplete.QuerySuggestionService;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.project.Project;
import com.intellij.ui.TextFieldWithAutoCompletion;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Created by Godwin on 5/7/2018 12:26 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class SessionWindow implements KeyEventPostProcessor {
    private JFrame mFrame;
    private JPanel container;
    private JTree tableTree;
    private JTable tableDetails;
    private JSplitPane jSplitPane;
    private JScrollPane jScrollTree;

    private JScrollPane jScrollTable;
    private JPanel jRightContainer;
    private JPanel jInnerContainer;
    private JPanel jBottomControl;
    private JButton btnSubmit;
    private Editor mInputEditor;
    private TextFieldWithAutoCompletion mTextFieldWithAutoCompletion;

    private Project mProject;
    private Disposable parent;

    private ClientSocket socket;
    private IBaseModel mBaseModel;

    private DDatabase mDatabase;
    private DTable mTable;

    private DataCommunicationListener listener = new DataCommunicationListener() {
        @Override
        public void onGetAppData(ClientSocket socket) {

        }

        @Override
        public void onGetDbData(List<DDatabase> databaseList) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(socket.getApplication());

            for (int i = 0; i < databaseList.size(); i++) {
                DDatabase database = databaseList.get(i);
                DefaultMutableTreeNode dNode = new DefaultMutableTreeNode(database);
                if (database.getTables() != null && database.getTables().size() > 0) {
                    for (int j = 0; j < database.getTables().size(); j++) {
                        DTable table = database.getTables().get(j);
                        DefaultMutableTreeNode tNode = new DefaultMutableTreeNode(table);
                        dNode.add(tNode);
                    }
                }
                root.add(dNode);
            }

            tableTree.setModel(new DefaultTreeModel(root));
        }

        @Override
        public void onGetTableDetails(List<List<String>> table, List<String> header) {
            try {
                String[][] array = new String[table.size()][];
                for (int i = 0; i < table.size(); i++) {
                    List<String> row = table.get(i);
                    try {
                        array[i] = row.toArray(new String[row.size()]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                DefaultTableModel model = new DefaultTableModel(array, header.toArray());

                tableDetails.setModel(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onGetQueryResult(List<List<String>> table, List<String> header) {

            ApplicationManager.getApplication().invokeLater(() -> {
                QuerySnapshotWindow window = new QuerySnapshotWindow(table, header, mFrame.getSize());
                window.setVisible();
            });
        }

        @Override
        public void onGetQueryFail(int errorCode, String errorMessage) {
            ApplicationManager.getApplication().invokeLater(() -> {
                ErrorAlertDialog dialog = new ErrorAlertDialog(errorMessage, mFrame.getSize());
                dialog.setVisible(true);
                dialog.toFront();
            });
        }

        @Override
        public void onCloseClient(ClientSocket socket) {

        }

        @Override
        public void onConnectClient(ClientSocket socket) {

        }
    };

    public SessionWindow(Project mProject, Disposable parent, ClientSocket socket) {
        super();
        this.mProject = mProject;
        this.parent = parent;
        this.socket = socket;

        mFrame = new JFrame();

        mFrame.setSize(new Dimension(1024, 500));
        mFrame.setResizable(true);
        mFrame.setTitle(socket.getApplication().getAppName() + "(" + socket.getApplication().getPackageName() + ")");
        mFrame.setLayout(new BorderLayout());
        mFrame.add(container, BorderLayout.CENTER);
        mFrame.pack();
        mFrame.setVisible(true);

        jSplitPane.setResizeWeight(.4f);

        populateDatabase();

        tableTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tableTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    tableTree.getLastSelectedPathComponent();

            /* if nothing is selected */
            if (node == null) return;

            mBaseModel = (IBaseModel) node.getUserObject();
            if (mBaseModel instanceof DTable) {
                showTable();
                mTable = (DTable) mBaseModel;
                populateTable(mTable);
            } else if (mBaseModel instanceof DDatabase) {
                mDatabase = (DDatabase) mBaseModel;
                showEditorPane();
            }
        });

        btnSubmit.addActionListener(e -> {
            if (mTextFieldWithAutoCompletion != null) {
                String query = mTextFieldWithAutoCompletion.getText();
                query = query.replaceAll(mDatabase.getName() + "> ", "");
                query =query.trim();
                if (!TextUtils.isEmpty(query)) {
                    executeQuery(mDatabase, query);
                }
            }
        });
        jBottomControl.setVisible(false);
    }

    private TextFieldWithAutoCompletion getTextFieldWithAutoCompletion() {

        TextFieldWithAutoCompletion<String> completion = TextFieldWithAutoCompletion.create(mProject, QuerySuggestionService.getDbTokensAsList(mDatabase), true, "");
        completion.setVisible(true);
        TextFieldWithAutoCompletion.StringsCompletionProvider provider = new TextFieldWithAutoCompletion.StringsCompletionProvider(QuerySuggestionService.getDbTokensAsList(mDatabase), null);

        completion.installProvider(provider);

        completion.addDocumentListener(new DocumentListener() {
            @Override
            public void beforeDocumentChange(DocumentEvent event) {

            }

            @Override
            public void documentChanged(DocumentEvent event) {
                try {
                    String text = event.getDocument().getText();

                    if (!text.startsWith(mDatabase.getName() + "> ") && text.length() < (mDatabase.getName() + "> ").length()) {
                        ApplicationManager.getApplication().invokeLater(() -> {
                            event.getDocument().setText(mDatabase.getName() + "> ");
                            completion.getEditor().getCaretModel().moveToOffset((mDatabase.getName() + "> ").length());
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        completion.setText(mDatabase.getName() + "> ");
        return completion;
    }

    @Override
    public boolean postProcessKeyEvent(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//            //Activate the refresh button:
//            if (mTextFieldWithAutoCompletion != null) {
//                String query = mTextFieldWithAutoCompletion.getText();
//                if (!TextUtils.isEmpty(query)) {
//                    executeQuery(mDatabase, query);
//                    return true;    //halt further processing
//                }
//            }
//        }
        return false;
    }

    private void populateDatabase() {
        socket.requestDbDetails();
        DataObserver.getInstance().subscribe(listener);
    }

    private void showEditorPane() {
        /*if (null == mInputEditor) {
            mInputEditor = createEditor();
            mInputEditor.getComponent().setVisible(true);
        } */
        if (null == mTextFieldWithAutoCompletion) {
            mTextFieldWithAutoCompletion = getTextFieldWithAutoCompletion();
        }

        jBottomControl.setVisible(true);
        jRightContainer.removeAll();

        jRightContainer.add(mTextFieldWithAutoCompletion, BorderLayout.CENTER);

        jRightContainer.repaint();
        jRightContainer.revalidate();
    }

    private void showTable() {
        if (null == tableDetails) {
            DefaultTableModel model = new DefaultTableModel();
            tableDetails = new JBTable(model);
            tableDetails.setVisible(true);
        }

        jBottomControl.setVisible(false);
        jRightContainer.removeAll();

        jRightContainer.add(new JScrollPane(tableDetails), BorderLayout.CENTER);
        jRightContainer.repaint();
        jRightContainer.revalidate();
    }

    public void addWindowListener(WindowAdapter adapter) {
        mFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DataObserver.getInstance().unSubcribe(listener);
                super.windowClosing(e);
                adapter.windowClosing(e);
            }
        });
    }

    private void populateTable(DTable table) {
        socket.requestTableDetails(table);
        DataObserver.getInstance().subscribe(listener);
    }

    private void executeQuery(DDatabase database, String query) {
        socket.executeQuery(database, query);
    }

    public void close() {
        mFrame.dispatchEvent(new WindowEvent(mFrame, WindowEvent.WINDOW_CLOSING));
    }

}
