package Leet_Cod;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import static utils.UserUtils.getUsername;

public class MyResponses extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTable responsesTable;
    private DefaultTableModel tableModel;
    private String userFolderPath;

    public MyResponses(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setBackground(new Color(227, 227, 216));
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("💾 My Saved Responses", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        add(title, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel(new Object[]{"Sr. No", "File Name", "Actions"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only Actions column is editable (buttons)
            }
        };
        responsesTable = new JTable(tableModel);
        responsesTable.setRowHeight(30);
        responsesTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        responsesTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(responsesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("🔙 Back");
        backButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 24));
        backButton.setBackground(new Color(134, 209, 183));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "leetcode"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadResponses();
    }

    private void loadResponses() {

        //file seperator is basically / or \ deopending on the system
        userFolderPath = "responses" + File.separator + getUsername();
        tableModel.setRowCount(0); // Clear table
        String username = getUsername();
        if (username == null || username.isEmpty()) return;

        // Make sure username is trimmed
        username = username.trim();

        // Construct absolute path relative to project root
        File userDir = new File("responses" + File.separator + username);
        System.out.println("Looking in: " + userDir.getAbsolutePath());

        if (!userDir.exists() || !userDir.isDirectory()) return;

        File[] files = userDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (files != null && files.length > 0) {
            int srNo = 1;
            for (File f : files) {
                tableModel.addRow(new Object[]{srNo++, f.getName(), "Edit / Delete"});
            }
        } else {
            System.out.println("No files found for user: " + username);
        }
    }



    // Button renderer for JTable
    private static class ButtonRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            add(editButton);
            add(deleteButton);
        }

        //tells which cell button we are using
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Button editor for JTable
    private class ButtonEditor extends DefaultCellEditor {
        protected JPanel panel;
        protected JButton editButton;
        protected JButton deleteButton;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            editButton = new JButton("Edit");
            editButton.setBackground(new Color(134, 209, 183)); // ✅ Pista

            deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(134, 209, 183)); // ✅ Pista


            panel.add(editButton);
            panel.add(deleteButton);

            editButton.addActionListener(e -> editResponse(selectedRow));
            deleteButton.addActionListener(e -> deleteResponse(selectedRow));
        }

        @Override
        //which part we need to edit  will return that row
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            selectedRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Edit / Delete";
        }
    }

    private void editResponse(int row) {
        String fileName = (String) tableModel.getValueAt(row, 1);
        File file = new File(userFolderPath, fileName);

        try {
            StringBuilder content = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) content.append(line).append("\n");
            br.close();

            // Open simple editor dialog
            JTextArea editorArea = new JTextArea(20, 60);
            editorArea.setText(content.toString());
            JScrollPane scrollPane = new JScrollPane(editorArea);
            int result = JOptionPane.showConfirmDialog(null, scrollPane, "Edit: " + fileName,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(editorArea.getText());
                bw.close();
                JOptionPane.showMessageDialog(null, "✅ Saved changes to " + fileName);
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "⚠ Error editing file: " + ex.getMessage());
        }
    }

    private void deleteResponse(int row) {
        String fileName = (String) tableModel.getValueAt(row, 1);
        File file = new File(userFolderPath, fileName);
        File recycleBin = new File("responses" + File.separator + ".recycle_bin");
        if (!recycleBin.exists()) recycleBin.mkdirs();
        File target = new File(recycleBin, fileName);

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to delete " + fileName + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        JOptionPane.showMessageDialog(null, "⚠ Failed to delete " + fileName);
    }
    public JPanel getPanel() {
        return this;
    }
}
