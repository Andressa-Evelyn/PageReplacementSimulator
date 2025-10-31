package br.com.simulador.ui;

import br.com.simulador.model.PageReplacementSimulator;
import br.com.simulador.algoritmos.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class SimulatorGUI extends JFrame {
    private JTextField fileField;
    private JTextField framesField;
    private JTextArea outputArea;

    public SimulatorGUI() {
        super("💾 Simulador de Substituição de Páginas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // 🎨 Cores do tema
        Color bgColor = new Color(245, 248, 255);
        Color accentColor = new Color(70, 130, 180);
        Color buttonColor = new Color(100, 149, 237);
        Color outputBg = new Color(250, 250, 250);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 🔹 Título
        JLabel title = new JLabel("Simulador de Substituição de Páginas", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(accentColor);
        mainPanel.add(title, BorderLayout.NORTH);

        // 🔹 Painel de entrada (arquivo + frames + botão)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel fileLabel = new JLabel("Arquivo:");
        fileLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(fileLabel, gbc);

        fileField = new JTextField(25);
        fileField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        inputPanel.add(fileField, gbc);

        JButton browse = new JButton("Escolher arquivo");
        browse.setBackground(buttonColor);
        browse.setForeground(Color.WHITE);
        browse.setFocusPainted(false);
        browse.setFont(new Font("Segoe UI", Font.BOLD, 13));
        browse.addActionListener(e -> escolherArquivo());
        gbc.gridx = 3; gbc.gridy = 0; gbc.gridwidth = 1;
        inputPanel.add(browse, gbc);

        JLabel framesLabel = new JLabel("Frames:");
        framesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(framesLabel, gbc);

        framesField = new JTextField("3", 6);
        framesField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(framesField, gbc);

        JButton runBtn = new JButton("Executar");
        runBtn.setBackground(new Color(46, 139, 87));
        runBtn.setForeground(Color.WHITE);
        runBtn.setFocusPainted(false);
        runBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        runBtn.setPreferredSize(new Dimension(120, 30));
        runBtn.addActionListener(e -> executarSimulacoes());
        gbc.gridx = 3; gbc.gridy = 1;
        inputPanel.add(runBtn, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // 🔹 Área de saída
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBackground(outputBg);
        outputArea.setBorder(new LineBorder(new Color(220, 220, 220)));
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setPreferredSize(new Dimension(750, 250));
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados da Simulação"));
        mainPanel.add(scroll, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void escolherArquivo() {
        JFileChooser chooser = new JFileChooser();
        int ret = chooser.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            fileField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void executarSimulacoes() {
        String filePath = fileField.getText().trim();
        int frames;

        try {
            frames = Integer.parseInt(framesField.getText().trim());
            if (frames <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe um número de frames válido (> 0).", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<Integer> paginas = PageReplacementSimulator.readPageReferencesFromFile(filePath);

            int fifo = Fifo.executar(paginas, frames);
            int otimo = Otimo.executar(paginas, frames);
            int lfu = Lfu.executar(paginas, frames);
            int nfu = Nfu.executar(paginas, frames);
            int clock = Clock.executar(paginas, frames);

            StringBuilder sb = new StringBuilder();
            sb.append("📄 Sequência: ").append(paginas).append("\n");
            sb.append("🧠 Frames: ").append(frames).append("\n\n");
            sb.append("💡 Resultados:\n");
            sb.append("  • FIFO  : ").append(fifo).append(" faltas de página\n");
            sb.append("  • ÓTIMO : ").append(otimo).append(" faltas de página\n");
            sb.append("  • LFU   : ").append(lfu).append(" faltas de página\n");
            sb.append("  • NFU   : ").append(nfu).append(" faltas de página\n");
            sb.append("  • CLOCK : ").append(clock).append(" faltas de página\n");

            outputArea.setText(sb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao ler arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulatorGUI().setVisible(true));
    }
}
