package edu.ucc.patterns.ui;

import edu.ucc.patterns.model.ShippingOrder;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public final class OrderTableModel extends AbstractTableModel {
    private final String[] columns = {"Guía", "Destinatario", "Proveedor", "Estado", "Entrega estimada"};
    private final List<ShippingOrder> orders = new ArrayList<>();

    public void add(ShippingOrder order) { orders.add(0, order); fireTableDataChanged(); }
    public ShippingOrder get(int row) { return orders.get(row); }
    @Override public int getRowCount() { return orders.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int column) { return columns[column]; }
    @Override public Object getValueAt(int row, int column) {
        ShippingOrder order = orders.get(row);
        return switch (column) {
            case 0 -> order.trackingCode();
            case 1 -> order.request().recipient();
            case 2 -> order.quote().provider();
            case 3 -> order.status();
            case 4 -> order.quote().estimatedDelivery();
            default -> "";
        };
    }
}
