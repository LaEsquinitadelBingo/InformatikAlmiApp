import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField txtPass;
	private JTextField txtCuenta;
	private JLabel lblTitulo;
	private JLabel lblCuenta;
	private JLabel lblPass;
	private JButton btnLogin;
	private JButton btnEmpresa;
	private EventosLogin eventos;
	private BaseDatos bbdd;

	// Este es el login de nuestro programa
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Una ventana muy sencilla con 2 textFields y varios botones para loguear en el programa
	public Login() {
		bbdd = new BaseDatos();
		bbdd.conectar();

		try {	
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Informatik Almi L:ogin");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTitulo = new JLabel("Conexion Empleado");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
		lblTitulo.setBounds(125, 125, 251, 41);
		contentPane.add(lblTitulo);
		
		lblCuenta = new JLabel("Cuenta");
		lblCuenta.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCuenta.setBounds(125, 177, 56, 19);
		contentPane.add(lblCuenta);
		
		lblPass = new JLabel("Contraseña");
		lblPass.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPass.setBounds(125, 207, 81, 19);
		contentPane.add(lblPass);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(216, 206, 160, 20);
		contentPane.add(txtPass);
		txtPass.setColumns(10);
		
		txtCuenta = new JTextField();
		txtCuenta.setColumns(10);
		txtCuenta.setBounds(216, 176, 160, 20);
		contentPane.add(txtCuenta);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(287, 237, 89, 23);
		contentPane.add(btnLogin);
		
		btnEmpresa = new JButton("Cuenta Empresa");
		btnEmpresa.setBounds(185, 294, 130, 35);
		contentPane.add(btnEmpresa);
		
		eventos = new EventosLogin(this);
	}

	public JTextField getTxtCuenta() {
		return txtCuenta;
	}

	public void setTxtCuenta(JTextField txtCuenta) {
		this.txtCuenta = txtCuenta;
	}

	public JTextField getTxtPass() {
		return txtPass;
	}

	public void setTxtPass(JPasswordField txtPass) {
		this.txtPass = txtPass;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public JButton getBtnEmpresa() {
		return btnEmpresa;
	}

	public void setBtnEmpresa(JButton btnEmpresa) {
		this.btnEmpresa = btnEmpresa;
	}
	
	public BaseDatos getBBDD() {
		return bbdd;
	}
}
