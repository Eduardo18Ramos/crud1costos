package Graficos;

import javax.swing.*;
import java.awt.FlowLayout;
import java.sql.*;

public class Welcome extends JFrame {

    // Componentes de la interfaz para el CRUD de Montos
    private JLabel lb_id, lb_nombre, lb_velocidad,lb_cantidad, lb_periodo, lb_description, lb_fecha;
    private JTextField txt_id, txt_nombre, txt_velocidad ,txt_cantidad, txt_periodo, txt_description, txt_fecha;
    private JMenuBar mb;
    private JMenu archivo, bd;
    private JMenuItem bd_C, bd_R;
    
    // Botones de la interfaz
    private JButton btn_buscar, btn_consultar, btn_alta, btn_actualizar, btn_eliminar;

    public Welcome() {
        // Configuración 
        super("CRUD de Montos");
        this.setLayout(new FlowLayout());
        this.setSize(420, 420);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Inicialización de etiquetas y campos de texto
        lb_id = new JLabel("ID:");
        txt_id = new JTextField(15);
        
        lb_nombre = new JLabel("Nombre:");
        txt_nombre = new JTextField(15);
        
        lb_velocidad = new JLabel("velocidad:");
        txt_velocidad = new JTextField(15);

        lb_cantidad = new JLabel("Cantidad:");
        txt_cantidad = new JTextField(15);

        lb_periodo = new JLabel("Periodo:");
        txt_periodo = new JTextField(15);

        lb_description = new JLabel("Description:");
        txt_description = new JTextField(15);

        lb_fecha = new JLabel("Fecha:");
        txt_fecha = new JTextField(15);

        // Inicialización de los botones
        btn_buscar = new JButton("Buscar x ID");
        btn_consultar = new JButton("Consultar");
        btn_alta = new JButton("Alta");
        btn_actualizar = new JButton("Actualizar");
        btn_eliminar = new JButton("Eliminar");

        // Agregar acciones a los botones
        btn_alta.addActionListener(e -> this.insertar());
        btn_consultar.addActionListener(e -> this.consultar());
        btn_buscar.addActionListener(e -> this.buscarPorId());
        btn_actualizar.addActionListener(e -> this.actualizar());
        btn_eliminar.addActionListener(e -> this.eliminar());

        //  menú superior
        this.menu();

        //componentes al contenedor en orden visual
        this.add(lb_id);
        this.add(txt_id);
        this.add(lb_nombre);
        this.add(txt_nombre);
        this.add(lb_velocidad);
        this.add(txt_velocidad);
        this.add(lb_cantidad);
        this.add(txt_cantidad);
        this.add(lb_periodo);
        this.add(txt_periodo);
        this.add(lb_description);
        this.add(txt_description);
        this.add(lb_fecha);
        this.add(txt_fecha);

        // Agregar los botones
        this.add(btn_buscar);
        this.add(btn_consultar);
        this.add(btn_alta);
        this.add(btn_actualizar);
        this.add(btn_eliminar);
       

        this.setVisible(true);
    }

    // Alta / Insertar registros
    public void insertar() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026ti32", "root", "");
            String sql = "INSERT INTO Personas (nombre, velocidad, cantidad, periodo, description, fecha) VALUES (?, ?,?,?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setString(1, this.txt_nombre.getText());
            ps.setString(2, this.txt_velocidad.getText());
            ps.setString(3, this.txt_cantidad.getText());
           ps.setString(4, this.txt_periodo.getText());
            ps.setString(5, this.txt_description.getText());
            ps.setString(6, this.txt_fecha.getText());
       

            ps.execute();
            JOptionPane.showMessageDialog(rootPane, "Monto registrado correctamente ...");

            ps.close();
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error conexion: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: Formato numérico inválido en la cantidad.");
        }
    }

    // Consultar todos los registros
    public void consultar() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026ti32", "root", "");
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM Personas");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "|" + rs.getString("nombre") + " | " +  rs.getInt("velocidad") + " | $" + rs.getInt("cantidad") + " | " + 
                                   rs.getString("periodo") + " | " + rs.getString("description") + " | " + rs.getString("fecha"));
            }

            rs.close();
            ps.close();
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error conexion: " + ex.getMessage());
        }
    }

    // Buscar por ID específico
    public void buscarPorId() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026ti32", "root", "");
            String sql = "SELECT * FROM Personas WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(this.txt_id.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                this.txt_nombre.setText(rs.getString("nombre"));
                 this.txt_cantidad.setText(rs.getString("cantidad"));
                this.txt_periodo.setText(rs.getString("periodo"));
                this.txt_description.setText(rs.getString("description"));
                this.txt_fecha.setText(rs.getString("fecha"));
            } else {
                JOptionPane.showMessageDialog(rootPane, "No se encontró ningún registro con ese ID.");
            }

            rs.close();
            ps.close();
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error conexion: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El ID debe ser un número válido.");
        }
    }

    // Actualizar registros
    public void actualizar() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026ti32", "root", "");
            String sql = "UPDATE Personas SET nombre = ? velocidad = ? cantidad = ?, periodo = ?, description = ?, fecha = ? WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setString(1, this.txt_nombre.getText());
            ps.setInt(2, Integer.parseInt(this.txt_velocidad.getText()));
            ps.setDouble(3, Double.parseDouble(this.txt_cantidad.getText()));
            ps.setString(4, this.txt_periodo.getText());
            ps.setString(5, this.txt_description.getText());
            ps.setString(6, this.txt_fecha.getText());
            ps.setInt(7, Integer.parseInt(this.txt_id.getText()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Registro actualizado correctamente ...");

            ps.close();
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error conexion: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: Verifique que la cantidad y el ID sean válidos.");
        }
    }

    // Eliminar registros
    public void eliminar() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026ti32", "root", "");
            String sql = "DELETE FROM Personas WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(this.txt_id.getText()));
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(rootPane, "Registro eliminado ...");
            } else {
                JOptionPane.showMessageDialog(rootPane, "No se encontró un registro con ese ID ...");
            }

            this.txt_nombre.setText("");
            this.txt_id.setText("");
            this.txt_cantidad.setText("");
            this.txt_velocidad.setText("");
            this.txt_periodo.setText("");
            this.txt_description.setText("");
            this.txt_fecha.setText("");

            ps.close();
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error conexion: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El campo ID debe contener un número válido.");
        }
    }

    // MENÚ SUPERIOR
    public void menu() {
        this.mb = new JMenuBar();
        this.setJMenuBar(mb);

        this.archivo = new JMenu("Archivo");
        this.mb.add(this.archivo);

        this.bd = new JMenu("Base de datos");
        this.mb.add(bd);

        // Opción: Crear
        this.bd_C = new JMenuItem("C - Crear");
        this.bd_C.addActionListener(e -> this.insertar());
        this.bd.add(this.bd_C);

        // Opción: Consultar
        this.bd_R = new JMenuItem("R - Consultar");
        this.bd_R.addActionListener(e -> this.consultar());
        this.bd.add(this.bd_R);
    }

    // Método principal
    public static void main(String[] args) {
        Welcome w = new Welcome();
    }
}
