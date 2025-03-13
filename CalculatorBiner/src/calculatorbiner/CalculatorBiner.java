package calculatorbiner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorBiner extends JFrame {
    private JTextField inputBiner1, inputBiner2, inputDesimal1, inputDesimal2;
    private JComboBox<String> operasiBox;
    private JButton hitungButton, resetButton;
    private JLabel hasilBinerLabel, hasilDesimalLabel;

    public CalculatorBiner() {
        setTitle("Binary Calculator");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Judul
        JLabel titleLabel = new JLabel("Binary Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.add(new JLabel("Bilangan Biner 1: "));
        inputBiner1 = new JTextField(10);
        panel1.add(inputBiner1);
        panel1.add(new JLabel("Desimal 1: "));
        inputDesimal1 = new JTextField(10);
        panel1.add(inputDesimal1);
        inputPanel.add(panel1);

        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.add(new JLabel("Bilangan Biner 2: "));
        inputBiner2 = new JTextField(10);
        panel2.add(inputBiner2);
        panel2.add(new JLabel("Desimal 2: "));
        inputDesimal2 = new JTextField(10);
        panel2.add(inputDesimal2);
        inputPanel.add(panel2);

        JPanel panel3 = new JPanel(new FlowLayout());
        panel3.add(new JLabel("Pilih Operasi: "));
        String[] operasi = {"+", "-", "*", "/"};
        operasiBox = new JComboBox<>(operasi);
        panel3.add(operasiBox);
        inputPanel.add(panel3);

        add(inputPanel, BorderLayout.CENTER);

        // Tombol Hitung & Reset
        hitungButton = new JButton("Hitung");
        resetButton = new JButton("Reset");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitungButton);
        buttonPanel.add(resetButton);

        // Panel Hasil
        JPanel hasilPanel = new JPanel(new GridLayout(2, 1));
        hasilPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hasilBinerLabel = new JLabel("Hasil (Biner): ", SwingConstants.CENTER);
        hasilDesimalLabel = new JLabel("Hasil (Desimal): ", SwingConstants.CENTER);
        hasilPanel.add(hasilBinerLabel);
        hasilPanel.add(hasilDesimalLabel);

        // Gabungkan hasilPanel dan buttonPanel dalam satu panel
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(hasilPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);

        // Event Listener untuk input
        inputBiner1.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                enforceBinaryInput(inputBiner1);
                syncBinaryToDecimal(inputBiner1, inputDesimal1);
            }
        });

        inputDesimal1.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                enforceDecimalInput(inputDesimal1);
                syncDecimalToBinary(inputDesimal1, inputBiner1);
            }
        });

        inputBiner2.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                enforceBinaryInput(inputBiner2);
                syncBinaryToDecimal(inputBiner2, inputDesimal2);
            }
        });

        inputDesimal2.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                enforceDecimalInput(inputDesimal2);
                syncDecimalToBinary(inputDesimal2, inputBiner2);
            }
        });

        // Event Listener untuk tombol
        hitungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungHasil();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });
    }

    private void enforceBinaryInput(JTextField binField) {
        String text = binField.getText();
        binField.setText(text.replaceAll("[^01]", ""));
    }

    private void enforceDecimalInput(JTextField decField) {
        String text = decField.getText();
        if (!text.matches("\\d*")) {
            JOptionPane.showMessageDialog(this, "Error: Hanya angka yang diperbolehkan dalam input desimal!", "Input Tidak Valid", JOptionPane.ERROR_MESSAGE);
            decField.setText(text.replaceAll("[^\\d]", "")); // Remove invalid characters
        }
    }

    private void syncBinaryToDecimal(JTextField binField, JTextField decField) {
        try {
            String binText = binField.getText();
            if (!binText.matches("[01]*")) return;
            int decimalValue = binText.isEmpty() ? 0 : Integer.parseInt(binText, 2);
            decField.setText(String.valueOf(decimalValue));
        } catch (Exception ignored) {}
    }

    private void syncDecimalToBinary(JTextField decField, JTextField binField) {
        try {
            String decText = decField.getText();
            if (!decText.matches("\\d*")) return;
            int decimalValue = decText.isEmpty() ? 0 : Integer.parseInt(decText);
            binField.setText(Integer.toBinaryString(decimalValue));
        } catch (Exception ignored) {}
    }

    private void hitungHasil() {
        try {
            int angka1 = Integer.parseInt(inputDesimal1.getText());
            int angka2 = Integer.parseInt(inputDesimal2.getText());

            char operasi = operasiBox.getSelectedItem().toString().charAt(0);
            int hasilDesimal = 0;
            boolean valid = true;

            switch (operasi) {
                case '+':
                    hasilDesimal = angka1 + angka2;
                    break;
                case '-':
                    hasilDesimal = angka1 - angka2;
                    break;
                case '*':
                    hasilDesimal = angka1 * angka2;
                    break;
                case '/':
                    if (angka2 == 0) {
                        JOptionPane.showMessageDialog(this, "Error: Pembagian dengan nol tidak diperbolehkan!", "Error", JOptionPane.ERROR_MESSAGE);
                        valid = false;
                    } else {
                        hasilDesimal = angka1 / angka2;
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Error: Operasi tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    valid = false;
            }

            if (valid) {
                String hasilBiner = Integer.toBinaryString(hasilDesimal);
                hasilBinerLabel.setText("Hasil (Biner): " + hasilBiner);
                hasilDesimalLabel.setText("Hasil (Desimal): " + hasilDesimal);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        inputBiner1.setText("");
        inputBiner2.setText("");
        inputDesimal1.setText("");
        inputDesimal2.setText("");
        hasilBinerLabel.setText("Hasil (Biner): ");
        hasilDesimalLabel.setText("Hasil (Desimal): ");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorBiner().setVisible(true);
        });
    }
}
