import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class EventosPanelConfiguracion {
	private PanelConfigurador panel;
	
	public EventosPanelConfiguracion(PanelConfigurador p) {
		panel = p;
		registrarEventos();
	}
	
	public void registrarEventos() {
		
		// Evento para cada uno de los comboBox, Lo que hacen es avanzar el estado y los propios arrays se encargaran de saber a que paso tienen que avanzar
		for (JComboBox cb: panel.getCombos()) {
			cb.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (cb.getSelectedIndex()>0) {
						panel.añadir(panel.getProductos().get(panel.getEstado() - 1).get(cb.getSelectedIndex() - 1));
						panel.setEstado(panel.nuevoPaso(panel.getEstado()));
					}
				}
			});
		}
		
		// Boton reiniciar que vacia todos los paneles, la configuracion del pc y vuelve al paso 1
		panel.getBtnReiniciar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i = 0;i<7;i++) {
					panel.getProductos().get(i).clear();
					panel.getCombos().get(i).setEnabled(false);
					panel.getCombos().get(i).removeAllItems();
					panel.getCombos().get(i).addItem("----------------------------------------------------------------------------");
					panel.getLabels().get(i).setText(".............");
					panel.getImagenes().get(i).setIcon(new ImageIcon(panel.getOriginales().get(i)));
					panel.getConfig().clear();
					panel.setEstado(0);
					panel.setEstado(panel.nuevoPaso(panel.getEstado()));
				}
				panel.getLblPrecio().setText("");
				panel.getBtnCarrito().setEnabled(false);
			}
		});
		
		// Boton guardar que nos permite almacenar nuestra setup en un archivo de texto formateado para que podamos cargarlo en cualquier momento
		panel.getBtnGuardar().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
		        // Llama a tu función para guardar los datos en un archivo de texto
		       
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int seleccion = fileChooser.showSaveDialog(null);

				if (seleccion == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					
					String fileName = JOptionPane.showInputDialog("Ingrese el nombre del archivo");
					file = new File(file, fileName);

					try {
						FileWriter writer = new FileWriter(file);
						BufferedWriter buffer = new BufferedWriter(writer);
						int i = 0;
						for (Producto producto : panel.getConfig()) {
							// Suponiendo que getDatosImportantes() es una función que devuelve una cadena con los datos importantes del producto
							buffer.write(producto.getId() + "||" + producto.getNombre() + "||" + producto.getDesc() + "||" + producto.getFoto().substring(producto.getFoto().indexOf('s')+1) + "||" + producto.getPrecio() + "||" + producto.getStock() + "||" + producto.getTipo() + "||" + panel.getCombos().get(i).getSelectedIndex());
							buffer.newLine();
							i++;
						}

						buffer.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
		    }
		});
		
	    // Boton para cargar un archivo de texto para leer una configuracion creada anteriormente
		panel.getBtnCargar().addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		        int seleccion = fileChooser.showOpenDialog(null);

		        if (seleccion == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();

		            try {
		                Scanner scanner = new Scanner(file);
		                panel.getBtnCarrito().setEnabled(false);
		                panel.setEstado(0);
		                panel.setEstado(panel.nuevoPaso(panel.getEstado()));
		                while (scanner.hasNextLine()) {
		                    String linea = scanner.nextLine();
		                    String[] partes = linea.split("\\|\\|");
		                    panel.getCombos().get(panel.getEstado()-1).setSelectedIndex(Integer.valueOf(7));
		                }

		                scanner.close();
		                
		            } catch (FileNotFoundException ex) {
		                
		            }
		        }
		    }
		});
		
		// Boton que añade todos los articulos de la configuracion al carrito del pedido actual
		panel.getBtnCarrito().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(Producto p: panel.getConfig()) {
					panel.getGestion().getCarro().add(p);
					panel.getGestion().getCarro().get(panel.getGestion().getCarro().size() - 1).setEnCarro(1);
				}
				for(int i = 0;i<8;i++) {
					panel.getProductos().get(i).clear();
					panel.getCombos().get(i).setEnabled(false);
					panel.getCombos().get(i).removeAllItems();
					panel.getCombos().get(i).addItem("----------------------------------------------------------------------------");
					panel.getLabels().get(i).setText(".............");
					panel.getImagenes().get(i).setIcon(new ImageIcon(panel.getOriginales().get(i)));
					panel.getConfig().clear();
					panel.setEstado(0);
					panel.setEstado(panel.nuevoPaso(panel.getEstado()));
				}
				panel.getLblPrecio().setText("");
				panel.getGestion().setCambiado(true);
				panel.getGestion().getContentPane().remove(panel);
				panel.getGestion().getContentPane().add(panel.getGestion().getMainPanel());
				panel.getGestion().cambiarBotones(true);
				panel.getGestion().actualizarCarro();
				panel.getGestion().getContentPane().revalidate();
				panel.getGestion().getContentPane().repaint();
				panel.getGestion().getMainPanel().repaint();
			}
		});
		
		
		// Boton para volver al panel principal y salir del creador de Pc
		panel.getBtnVolver().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i = 0;i<8;i++) {
					panel.getProductos().get(i).clear();
					panel.getCombos().get(i).setEnabled(false);
					panel.getCombos().get(i).removeAllItems();
					panel.getCombos().get(i).addItem("----------------------------------------------------------------------------");
					panel.getLabels().get(i).setText(".............");
					panel.getImagenes().get(i).setIcon(new ImageIcon(panel.getOriginales().get(i)));
					panel.getConfig().clear();
					panel.setEstado(0);
					panel.setEstado(panel.nuevoPaso(panel.getEstado()));
				}
				panel.getLblPrecio().setText("");
				panel.getGestion().setCambiado(true);
				panel.getGestion().getContentPane().remove(panel);
				panel.getGestion().getContentPane().add(panel.getGestion().getMainPanel());
				panel.getGestion().cambiarBotones(true);
				panel.getGestion().actualizarCarro();
				panel.getGestion().getContentPane().revalidate();
				panel.getGestion().getContentPane().repaint();
				panel.getGestion().getMainPanel().repaint();
			}
		});
	}
	
}
