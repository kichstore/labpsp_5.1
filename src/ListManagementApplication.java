import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListManagementApplication extends JFrame {
    private DefaultListModel<String> listModel1;
    private DefaultListModel<String> listModel2;
    private DefaultListModel<String> listModel3;

    public ListManagementApplication() {
        setTitle("List Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Создание списков
        listModel1 = new DefaultListModel<>();
        listModel2 = new DefaultListModel<>();
        listModel3 = new DefaultListModel<>();

        // Добавление элементов в первый список
        listModel1.addElement("Item 1");
        listModel1.addElement("Item 2");
        listModel1.addElement("Item 3");

        // Создание компонентов JList для каждого списка
        JList<String> list1 = new JList<>(listModel1);
        JList<String> list2 = new JList<>(listModel2);
        JList<String> list3 = new JList<>(listModel3);

        // Создание кнопок для управления списками
        JButton moveRightButton = new JButton("Move Right");
        JButton moveLeftButton = new JButton("Move Left");
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        // Обработчики событий для кнопок
        moveRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedItems(list1, listModel1, list2, listModel2);
                moveSelectedItems(list2, listModel2, list3, listModel3);
                moveSelectedItems(list3, listModel3, list1, listModel1);
            }
        });

        moveLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSelectedItems(list3, listModel3, list2, listModel2);
                moveSelectedItems(list2, listModel2, list1, listModel1);
                moveSelectedItems(list1, listModel1, list3, listModel3);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newItem = JOptionPane.showInputDialog(ListManagementApplication.this, "Enter a new item:");
                if (newItem != null && !newItem.isEmpty()) {
                    listModel2.addElement(newItem);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = list2.getSelectedValue();
                if (selectedItem != null) {
                    String editedItem = JOptionPane.showInputDialog(ListManagementApplication.this, "Edit item:", selectedItem);
                    if (editedItem != null && !editedItem.isEmpty()) {
                        listModel2.setElementAt(editedItem, list2.getSelectedIndex());
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list2.getSelectedIndex();
                if (selectedIndex != -1) {
                    int confirmResult = JOptionPane.showConfirmDialog(ListManagementApplication.this, "Are you sure you want to delete this item?", "Delete Item", JOptionPane.YES_NO_OPTION);
                    if (confirmResult == JOptionPane.YES_OPTION) {
                        listModel2.remove(selectedIndex);
                    }
                }
            }
        });

        // Панели для списков и кнопок
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(1, 3));
        listPanel.add(new JScrollPane(list1));
        listPanel.add(new JScrollPane(list2));
        listPanel.add(new JScrollPane(list3));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.add(moveRightButton);
        buttonPanel.add(moveLeftButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(listPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void moveSelectedItems(JList<String> sourceList, DefaultListModel<String> sourceModel, JList<String> destinationList, DefaultListModel<String> destinationModel) {
        int[] selectedIndices = sourceList.getSelectedIndices();
        for (int i = selectedIndices.length - 1; i >= 0; i--) {
            String item = sourceModel.getElementAt(selectedIndices[i]);
            sourceModel.removeElementAt(selectedIndices[i]);
            destinationModel.addElement(item);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ListManagementApplication app = new ListManagementApplication();
                app.setVisible(true);
            }
        });
    }
}