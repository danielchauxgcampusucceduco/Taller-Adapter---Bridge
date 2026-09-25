package edu.ucc.patterns.ui;

import edu.ucc.patterns.adapter.InternationalCarrierAdapter;
import edu.ucc.patterns.adapter.InternationalCarrierGateway;
import edu.ucc.patterns.adapter.LegacyCourierAdapter;
import edu.ucc.patterns.adapter.LegacyCourierSystem;
import edu.ucc.patterns.adapter.ShippingProvider;
import edu.ucc.patterns.bridge.DashboardChannel;
import edu.ucc.patterns.bridge.EmailChannel;
import edu.ucc.patterns.bridge.NotificationChannel;
import edu.ucc.patterns.bridge.SmsChannel;
import edu.ucc.patterns.model.ServiceLevel;
import edu.ucc.patterns.model.ShipmentQuote;
import edu.ucc.patterns.model.ShipmentRequest;
import edu.ucc.patterns.model.ShipmentStatus;
import edu.ucc.patterns.model.ShippingOrder;
import edu.ucc.patterns.service.InMemoryOrderRepository;
import edu.ucc.patterns.service.LogisticsService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Swing frontend that lets a user operate both patterns in one real-life flow. */
public final class ShippingDashboard extends JFrame {
    private final LogisticsService service = new LogisticsService(new InMemoryOrderRepository());
    private final List<ShippingProvider> providers = List.of(
            new LegacyCourierAdapter(new LegacyCourierSystem()),
            new InternationalCarrierAdapter(new InternationalCarrierGateway()));
    private final JTextField recipientField = new JTextField();
    private final JTextField destinationField = new JTextField();
    private final JTextField weightField = new JTextField("1.0");
    private final JComboBox<ServiceLevel> levelBox = new JComboBox<>(ServiceLevel.values());
    private final JComboBox<ShippingProvider> providerBox = new JComboBox<>(providers.toArray(ShippingProvider[]::new));
    private final JLabel quoteLabel = new JLabel("Complete los datos para consultar la cotización.");
    private final JCheckBox emailCheck = new JCheckBox("Correo electrónico", true);
    private final JCheckBox smsCheck = new JCheckBox("SMS");
    private final JCheckBox dashboardCheck = new JCheckBox("Panel interno", true);
    private final OrderTableModel tableModel = new OrderTableModel();
    private final JTable orderTable = new JTable(tableModel);
    private final JComboBox<ShipmentStatus> statusBox = new JComboBox<>(ShipmentStatus.values());

    public ShippingDashboard() {
        super("Envíos Conectados | Adapter + Bridge");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1080, 720);
        setMinimumSize(new java.awt.Dimension(900, 620));
        setLocationRelativeTo(null);
        buildInterface();
    }

    private void buildInterface() {
        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        root.setBackground(new Color(247, 249, 252));
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createContent(), BorderLayout.CENTER);
        add(root);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Envíos Conectados");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        JLabel subtitle = new JLabel("Caso de estudio: gestión de paquetería con Adapter y Bridge");
        subtitle.setForeground(new Color(85, 95, 110));
        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);
        return header;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new GridLayout(1, 2, 16, 0));
        content.setOpaque(false);
        content.add(createRegistrationPanel());
        content.add(createTrackingPanel());
        return content;
    }

    private JPanel createRegistrationPanel() {
        JPanel panel = card("1. Registrar envío");
        GridBagConstraints c = constraints();
        addField(panel, c, "Destinatario", recipientField);
        addField(panel, c, "Ciudad de destino", destinationField);
        addField(panel, c, "Peso (kg)", weightField);
        addField(panel, c, "Nivel de servicio", levelBox);
        addField(panel, c, "Proveedor", providerBox);

        c.gridx = 0; c.gridy++; c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL;
        JButton quoteButton = new JButton("Consultar cotización");
        quoteButton.addActionListener(event -> showQuote());
        panel.add(quoteButton, c);
        c.gridy++; c.insets = new Insets(10, 4, 10, 4);
        quoteLabel.setForeground(new Color(28, 80, 150));
        panel.add(quoteLabel, c);

        c.gridy++; c.insets = new Insets(4, 4, 4, 4);
        panel.add(new JLabel("Canales de notificación (Bridge)"), c);
        c.gridy++;
        JPanel channels = new JPanel(new GridLayout(1, 3));
        channels.setOpaque(false);
        channels.add(emailCheck); channels.add(smsCheck); channels.add(dashboardCheck);
        panel.add(channels, c);
        c.gridy++; c.insets = new Insets(16, 4, 4, 4);
        JButton registerButton = new JButton("Registrar envío y notificar");
        registerButton.setBackground(new Color(27, 115, 189));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(event -> registerOrder());
        panel.add(registerButton, c);

        c.gridy++; c.weighty = 1; c.anchor = GridBagConstraints.SOUTHWEST;
        JLabel explanation = new JLabel("Adapter: normaliza dos APIs de mensajería incompatibles.");
        explanation.setForeground(new Color(90, 100, 115));
        panel.add(explanation, c);
        return panel;
    }

    private JPanel createTrackingPanel() {
        JPanel panel = card("2. Seguimiento y alertas");
        GridBagConstraints c = constraints();
        c.gridx = 0; c.gridy = 0; c.weightx = 1; c.weighty = 1; c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.setRowHeight(26);
        panel.add(new JScrollPane(orderTable), c);
        c.gridy++; c.weighty = 0; c.gridwidth = 1; c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Nuevo estado"), c);
        c.gridx = 1;
        panel.add(statusBox, c);
        c.gridx = 0; c.gridy++; c.gridwidth = 2; c.insets = new Insets(12, 4, 4, 4);
        JButton updateButton = new JButton("Actualizar y enviar alertas");
        updateButton.addActionListener(event -> updateStatus());
        panel.add(updateButton, c);
        c.gridy++; c.insets = new Insets(16, 4, 4, 4);
        JLabel explanation = new JLabel("Bridge: combina tipos de alerta y canales sin acoplarlos.");
        explanation.setForeground(new Color(90, 100, 115));
        panel.add(explanation, c);
        return panel;
    }

    private JPanel card(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 232)), BorderFactory.createTitledBorder(title)));
        return panel;
    }

    private GridBagConstraints constraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(7, 8, 7, 8);
        c.anchor = GridBagConstraints.WEST;
        return c;
    }

    private void addField(JPanel panel, GridBagConstraints c, String label, java.awt.Component field) {
        c.gridx = 0; c.gridy++; c.gridwidth = 1; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(label), c);
        c.gridx = 1; c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, c);
    }

    private ShipmentRequest readRequest() {
        return new ShipmentRequest(recipientField.getText(), destinationField.getText(),
                new BigDecimal(weightField.getText().trim().replace(',', '.')), (ServiceLevel) levelBox.getSelectedItem());
    }

    private ShippingProvider selectedProvider() { return (ShippingProvider) providerBox.getSelectedItem(); }

    private void showQuote() {
        try {
            ShipmentQuote quote = service.quote(selectedProvider(), readRequest());
            quoteLabel.setText("Valor: " + formatMoney(quote.price()) + " · Entrega: " + quote.estimatedDelivery());
        } catch (Exception exception) { showError(exception); }
    }

    private void registerOrder() {
        try {
            ShippingOrder order = service.createOrder(selectedProvider(), readRequest());
            tableModel.add(order);
            String channels = service.changeStatus(order, ShipmentStatus.REGISTERED, selectedChannels()).stream()
                    .map(result -> result.channel()).reduce((first, second) -> first + ", " + second).orElse("ninguno");
            quoteLabel.setText("Envío " + order.trackingCode() + " registrado. Alertas: " + channels);
        } catch (Exception exception) { showError(exception); }
    }

    private void updateStatus() {
        int row = orderTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un envío de la tabla.", "Sin selección", JOptionPane.INFORMATION_MESSAGE); return; }
        ShippingOrder order = tableModel.get(orderTable.convertRowIndexToModel(row));
        try {
            var results = service.changeStatus(order, (ShipmentStatus) statusBox.getSelectedItem(), selectedChannels());
            tableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(this, "Estado actualizado. Alertas enviadas: " + results.size(), "Proceso exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exception) { showError(exception); }
    }

    private List<NotificationChannel> selectedChannels() {
        List<NotificationChannel> channels = new ArrayList<>();
        if (emailCheck.isSelected()) channels.add(new EmailChannel());
        if (smsCheck.isSelected()) channels.add(new SmsChannel());
        if (dashboardCheck.isSelected()) channels.add(new DashboardChannel());
        return channels;
    }

    private String formatMoney(BigDecimal value) {
        Locale colombianSpanish = new Locale.Builder().setLanguage("es").setRegion("CO").build();
        return NumberFormat.getCurrencyInstance(colombianSpanish).format(value);
    }
    private void showError(Exception exception) { JOptionPane.showMessageDialog(this, exception.getMessage(), "Datos inválidos", JOptionPane.ERROR_MESSAGE); }
}
