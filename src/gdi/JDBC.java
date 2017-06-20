package gdi;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class JDBC extends JFrame{
	// 	JDBC driver name and database URL
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	// URL to database
	static final String DB_URL = "jdbc:oracle://localhost/";
	
	//Path to image file
	static File image;
	
	//Connection and statement
	static Connection conn = null;
	static Statement stmt = null;
	
	//  Database credentials
	static final String USER = "sys";
	static final String PASS = "123";

	public JDBC() {
		this.setLayout(new FlowLayout());
		this.setSize(400, 600);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(new JLabel("Inserir imagem "));
		JButton inserir = new JButton("Inserir...");
		inserir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean enviou = false;
				JFileChooser chooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("*.jpg", "jpg");
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					image = chooser.getSelectedFile();
					 enviou = sendImageToDB(image);
				}
				
				if(enviou) {
					JOptionPane.showConfirmDialog(null, "Arquivo gravado com sucesso no banco de dados!");
				} else {
					JOptionPane.showConfirmDialog(null, "Houve um problema ao salvar a imagem!");
				}
			}
		});
		this.getContentPane().add(inserir);
		this.setVisible(true);
	}

	public static void main(String[] args) {
				
		try {
			new JDBC();
			//Loads oracle driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Connects to DB
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			//Manages queries
			stmt = conn.createStatement();
			
			//Test query
			PreparedStatement query = conn.prepareStatement("SELECT F.NOME AS NOME FROM TB_FUNCIONARIO");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static boolean sendImageToDB(File image, PreparedStatement ps) {
		boolean result = false;
		try {
			FileInputStream fis = new FileInputStream(image);
			ps.setBinaryStream(3, fis, (int)image.length());
			ps.execute();
			conn.commit();
			fis.close();
			result = true;
		} catch (Exception e) {
			
		} 
		return result;
	}

}
