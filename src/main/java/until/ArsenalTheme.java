package until;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class ArsenalTheme {
    
    // ========== MÀU SẮC CHÍNH ==========
    public static final Color RED = new Color(219, 0, 7);
    public static final Color DARK_RED = new Color(150, 0, 0);
    public static final Color LIGHT_RED = new Color(255, 230, 230);
    public static final Color GOLD = new Color(255, 204, 0);
    public static final Color WHITE = Color.WHITE;
    public static final Color BLACK = Color.BLACK;
    public static final Color GRAY = new Color(100, 100, 100);
    public static final Color LIGHT_GRAY = new Color(240, 240, 240);
    public static final Color BLUE_DARK = new Color(0, 51, 102);
    
    // ========== FONT CHỮ ==========
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    public static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 13);
    public static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 11);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
    
    // ========== STYLE BUTTON ==========
    public static void stylePrimaryButton(JButton btn) {
        btn.setBackground(RED);
        btn.setForeground(WHITE);
        btn.setFont(BUTTON_FONT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
    
    public static void styleSecondaryButton(JButton btn) {
        btn.setBackground(WHITE);
        btn.setForeground(RED);
        btn.setFont(NORMAL_FONT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public static void styleDangerButton(JButton btn) {
        btn.setBackground(DARK_RED);
        btn.setForeground(WHITE);
        btn.setFont(BUTTON_FONT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    // ========== STYLE LABEL ==========
    public static JLabel createTitleLabel(String text) {
        JLabel lbl = new JLabel(text, JLabel.CENTER);
        lbl.setFont(TITLE_FONT);
        lbl.setForeground(RED);
        return lbl;
    }
    
    public static JLabel createHeaderLabel(String text) {
        JLabel lbl = new JLabel(text, JLabel.CENTER);
        lbl.setFont(HEADER_FONT);
        lbl.setForeground(DARK_RED);
        return lbl;
    }
    
    // ========== STYLE TABLE ==========
    public static void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(NORMAL_FONT);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(RED);
        table.getTableHeader().setForeground(WHITE);
        table.setSelectionBackground(LIGHT_RED);
        table.setSelectionForeground(BLACK);
        table.setGridColor(LIGHT_GRAY);
    }
    
    // ========== STYLE PANEL ==========
    public static JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(RED);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return panel;
    }
    
    public static JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return panel;
    }
    
    // ========== BORDER ==========
    public static Border createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(RED, 1),
            title,
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            HEADER_FONT,
            RED
        );
    }
    
    // ========== DIALOG STYLE ==========
    public static void styleDialog(JDialog dialog) {
        dialog.getContentPane().setBackground(WHITE);
    }
    
    // ========== LOGIN BACKGROUND ==========
    public static JPanel createLoginBackground() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createLineBorder(RED, 2));
        return panel;
    }
}