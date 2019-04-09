package com.godwin.ui;


import com.godwin.model.DApplication;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Godwin on 5/9/2018 10:13 AM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class CellRenderer extends JLabel implements ListCellRenderer<DApplication> {
    @Override
    public Component getListCellRendererComponent(JList list, DApplication value, int index, boolean isSelected, boolean cellHasFocus) {
        ImageIcon imageIcon = value.getIcon();
        if (null == imageIcon) {
            imageIcon = new ImageIcon(getClass().getResource("/images/android.png"));
        }
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  //
        setIcon(imageIcon);

        //<html><FONT COLOR=RED>Red</FONT> and <FONT COLOR=BLUE>Blue</FONT> Text</html>
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");
        builder.append("<font size=\"5\"><b>")
                .append(value.getAppName())
                .append("</b>")
                .append("-v")
                .append(value.getVersion())
                .append("</font>");
        builder.append("<br><font size=\"3\">")
                .append(value.getPackageName())
                .append("</font>");
        builder.append("</body></html>");

        setText(builder.toString());
        return this;
    }
}
