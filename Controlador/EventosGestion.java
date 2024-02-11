import java.awt.BorderLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class EventosGestion {
    GestionComponentes gestion;
    public EventosGestion(GestionComponentes g) {
        // TODO Auto-generated constructor stub
        gestion = g;
        registrarEventos();
    }
    
    public void crearEventosPedido(JButton b1, JButton b2, JButton b3, JTextField txt, Producto producto) {
    	b1.addActionListener(new ActionListener() {
    		            @Override
    		 public void actionPerformed(ActionEvent e) {
    		                // TODO Auto-generated method stub
    		            	producto.setEnCarro(0);
    		            	gestion.setCambiado(true);
    						gestion.getCarro().remove(producto);
    						b1.getParent().getParent().getParent().getParent().remove(b1.getParent().getParent().getParent());
    						gestion.actualizarCarro();
    						gestion.calcularTotal();
    		                gestion.revalidate();
    		                gestion.repaint();
   
    		            }
    	});
    	b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	
            	txt.setText((producto.getEnCarro()-1) +"" );
            	gestion.setCambiado(true);
            	producto.setEnCarro(txt.getText().equals("") ? 0 : Integer.parseInt(txt.getText()));
            	if (producto.getEnCarro() == 0) {
            		gestion.getCarro().remove(producto);
            		b1.getParent().getParent().getParent().getParent().getParent().remove(b1.getParent().getParent().getParent().getParent());
            	}
            	gestion.actualizarCarro();
				gestion.calcularTotal();
                gestion.revalidate();
                gestion.repaint();
		            }
		});
    	b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	gestion.setCambiado(true);
            	txt.setText((producto.getEnCarro()+1) + "");
            	producto.setEnCarro(txt.getText().equals("") ? 0 : Integer.parseInt(txt.getText()));
            	if (producto.getEnCarro() == 0) {
            		gestion.getCarro().remove(producto);
            		b3.getParent().getParent().getParent().getParent().remove(b1.getParent().getParent().getParent());
            	}
            	gestion.actualizarCarro();
				gestion.calcularTotal();
                gestion.revalidate();
                gestion.repaint();
		    }
		});
    }
    
    public void crearEventosCompra(JButton btnComprar, JButton btnNuevo, JButton btnVolver, JTextField txt1,JTextField txt2, JTextField txt3, JTextField txt4) {
		btnComprar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (gestion.getCarro().size() == 0) {
					JOptionPane.showMessageDialog(gestion, "No hay productos en el carro", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					gestion.setGuardado(true);
					
					int aux = gestion.getLogin().getBBDD().insertarPedido(1, gestion.getArrayProductos(), gestion.getArrayCantidades(),gestion.getNumPedido());
					if (aux==0) JOptionPane.showMessageDialog(gestion, "No se pudo crear el pedido");
					else {
						gestion.setNumPedido(aux);
						gestion.getLblCarro().setText("Pedido nº " + aux);
						for(Producto p: gestion.getCarro()) {
							p.setNoEnStock(p.getEnCarro());
						}
					}
					gestion.getMainPanel().rellenarArticulos(gestion.getMainPanel().getTipo());
					gestion.actualizarCarro();
					gestion.getContentPane().remove(btnComprar.getParent().getParent());
					gestion.getContentPane().add(gestion.getMainPanel());
					gestion.setEstoyGestion(false);
					gestion.estoyGestion();
					gestion.getBtnFacturar().setEnabled(true);
					gestion.getContentPane().revalidate();
					gestion.getContentPane().repaint();
					gestion.getMainPanel().revalidate();
					gestion.getMainPanel().repaint();
				}
			}
		});
    	
    	btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            	if (gestion.getCarro().size() == 0) {
                    return;
                } else {
                    if (gestion.isCambiado()) {
                        int opcion = JOptionPane.showConfirmDialog(gestion, "Desea Borrar el Pedido Actual", "Borrar", JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) {
                            

                        } else {
                            return;
                        }
                    }
					for (Producto p : gestion.getCarro()) {
						p.setEnCarro(0);
					}
                    gestion.getCarro().clear();
                    //BaseDatos.actualizarStock();
                    gestion.actualizarCarro();
                    gestion.setCambiado(false);
                    gestion.getContentPane().remove(btnVolver.getParent().getParent());
    				gestion.getContentPane().add(gestion.getMainPanel());
    				gestion.setEstoyGestion(false);
    				gestion.setMiniaturas(true);
    				gestion.estoyGestion();
    				gestion.getContentPane().revalidate();
    				gestion.getContentPane().repaint();
    				gestion.getMainPanel().revalidate();
    				gestion.getMainPanel().repaint();
                }
            }
    	});
    	
		btnVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gestion.getContentPane().remove(btnVolver.getParent().getParent());
				gestion.getContentPane().add(gestion.getMainPanel());
				gestion.setEstoyGestion(false);
				gestion.setMiniaturas(true);
				gestion.estoyGestion();
				gestion.getContentPane().revalidate();
				gestion.getContentPane().repaint();
				gestion.getMainPanel().revalidate();
				gestion.getMainPanel().repaint();
			}
		});
    }


    public void registrarEventos() {
        gestion.getBtnMinCarro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                gestion.getContentPane().remove(gestion.getScrCarro());
                gestion.getContentPane().add(gestion.getPnlCarroMin(), BorderLayout.EAST);
                PanelPrincipal aux = (PanelPrincipal) gestion.getMainPanel();
                GridLayout currentLayout = (GridLayout) aux.getTablePanel().getLayout();

                // Crear un nuevo GridLayout con una columna adicional
                GridLayout newLayout = new GridLayout(currentLayout.getRows(), currentLayout.getColumns() + 1);
                // Establecer el nuevo layout
                aux.getTablePanel().setLayout(newLayout);
                aux.añadirArticulos(newLayout.getColumns());
                gestion.getContentPane().revalidate();
                gestion.getContentPane().repaint();
            }
        });

        gestion.getBtnCarroMinMin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                gestion.getContentPane().remove(gestion.getPnlCarroMin());
                gestion.getContentPane().add(gestion.getScrCarro(), BorderLayout.EAST);
                PanelPrincipal aux = (PanelPrincipal) gestion.getMainPanel();
                GridLayout currentLayout = (GridLayout) aux.getTablePanel().getLayout();

                // Crear un nuevo GridLayout con una columna adicional
                GridLayout newLayout = new GridLayout(currentLayout.getRows(), currentLayout.getColumns() - 1);
                // Establecer el nuevo layout
                aux.getTablePanel().setLayout(newLayout);
                aux.añadirArticulos(newLayout.getColumns());

                // Redibujar el contenedor
                gestion.getContentPane().revalidate();
                gestion.getContentPane().repaint();
            }
        });

        gestion.getBtnMinLista().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestion.getContentPane().remove(gestion.getPnlLista());
                gestion.getContentPane().add(gestion.getPnlListaMin(), BorderLayout.WEST);
                PanelPrincipal aux = (PanelPrincipal) gestion.getMainPanel();
                GridLayout currentLayout = (GridLayout) aux.getTablePanel().getLayout();

                // Crear un nuevo GridLayout con una columna adicional
                GridLayout newLayout = new GridLayout(currentLayout.getRows(), currentLayout.getColumns() + 1);
                // Establecer el nuevo layout
                aux.getTablePanel().setLayout(newLayout);
                aux.añadirArticulos(newLayout.getColumns());

                // Redibujar el contenedor
                gestion.getContentPane().revalidate();
                gestion.getContentPane().repaint();
            }
        });

        gestion.getBtnListMinMin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestion.getContentPane().remove(gestion.getPnlListaMin());
                gestion.getContentPane().add(gestion.getPnlLista(), BorderLayout.WEST);
                PanelPrincipal aux = (PanelPrincipal) gestion.getMainPanel();
                GridLayout currentLayout = (GridLayout) aux.getTablePanel().getLayout();

                // Crear un nuevo GridLayout con una columna adicional
                GridLayout newLayout = new GridLayout(currentLayout.getRows(), currentLayout.getColumns() - 1);
                // Establecer el nuevo layout
                aux.getTablePanel().setLayout(newLayout);
                aux.añadirArticulos(newLayout.getColumns());

                // Redibujar el contenedor
                gestion.getContentPane().revalidate();
                gestion.getContentPane().repaint();
            }
        });


        gestion.getLoginItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                gestion.getLogin().getTxtCuenta().setText("");
                gestion.getLogin().getTxtPass().setText("");    
                gestion.getLogin().setVisible(true);
                try {
 
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
                    
                    e1.printStackTrace();
                } catch (UnsupportedLookAndFeelException a) {
                    a.printStackTrace();
                }
                gestion.dispose();
            }
        });

        gestion.getExitItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });
        
		gestion.getBtnCargar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 
				 if (!gestion.getCarro().isEmpty() && (gestion.getNumPedido() == 0 || gestion.isCambiado())) {
                     int opcion = JOptionPane.showConfirmDialog(gestion, "Tiene un pedido sin guardar, desea borrarlo?", "Borrar", JOptionPane.YES_NO_OPTION);
                     if (opcion == JOptionPane.YES_OPTION) {
                         

                     } else {
                         return;
                     }
                 } 
				
				String input = JOptionPane.showInputDialog(null, "Numero de Pedido:");

				if (input != null && !input.isEmpty()) {
		            try {
		                // Convertir la entrada a un número entero
		                int number = Integer.parseInt(input);
		                try {
		        			ResultSet rs = gestion.getLogin().getBBDD().getPedido(number);
		        			ArrayList<Producto> carro = new ArrayList<>();
		        			
		        			do {
		        				Producto p = new Producto(rs.getInt("id_producto"),rs.getString("nombre"), rs.getString("descripcion"),rs.getString("imagen"), rs.getDouble("precio"), rs.getInt("stock"), 0);
		        				p.setEnCarro(rs.getInt("cantidad"));
		        				p.setNoEnStock(rs.getInt("cantidad"));
		        				carro.add(p);
		        			} while (rs.next());
		        			gestion.getLblCarro().setText("Pedido nº " + input);
		        			gestion.setNumPedido(Integer.valueOf(input));
		        			gestion.setCarro(carro);
		        			gestion.setCambiado(false);
		        			gestion.setMiniaturas(true);
		        			gestion.actualizarCarro();
		        			gestion.getBtnFacturar().setEnabled(true);
		                    gestion.revalidate();   
		                    gestion.repaint();
		        		} catch (SQLException o) {
		        			o.printStackTrace();
		        			System.out.println("Error en PanelPrincipal");
		        		} catch (NullPointerException n) {
		        			JOptionPane.showMessageDialog(null, "No existe ese pedido.");
		        		}
		            } catch (NumberFormatException t) {
		                // Manejar el error en caso de que la entrada no sea un número válido
		                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
		            }
		        } else {
		            // Manejar la situación si el usuario presiona "Cancelar" o cierra el cuadro de diálogo
		            JOptionPane.showMessageDialog(null, "Operación cancelada.");
		        }
			}
		});

		
        gestion.getBtnNuevo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (gestion.getCarro().size() == 0) {
                    return;
                } else {
                    if (gestion.getNumPedido() == 0 || gestion.isCambiado()) {
                        int opcion = JOptionPane.showConfirmDialog(gestion, "Tiene un pedido sin guardar, desea borrarlo?", "Borrar", JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) {
                            

                        } else {
                            return;
                        }
                    } 
                    for (Producto p : gestion.getCarro()) {
						p.setEnCarro(0);
					}
                    gestion.getLblCarro().setText("Nuevo Pedido");
                    gestion.getCarro().clear();
                    //BaseDatos.actualizarStock();
                    gestion.setMiniaturas(true);
                    gestion.setCambiado(false);
                    gestion.actualizarCarro();
                    gestion.revalidate();   
                    gestion.repaint();
                }
            }
        });
        
		gestion.getBtnGestionar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gestion.setEstoyGestion(true);
				gestion.crearPedido(gestion.getCarro());
				gestion.setMiniaturas(true);
			}
		});
		
		gestion.getBtnTramitar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gestion.setEstoyGestion(true);
				gestion.crearPedido(gestion.getCarro());
				gestion.setMiniaturas(true);
			}
		});
		
		gestion.getBtnPc().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gestion.getContentPane().remove(gestion.getMainPanel());
                gestion.setPanelConfigurador(new PanelConfigurador(gestion));
				gestion.getContentPane().add(gestion.getPanelConfigurador(), BorderLayout.CENTER);
                gestion.cambiarBotones(false);
                gestion.getContentPane().revalidate();
                gestion.getContentPane().repaint();
			}
		});
		
		gestion.getBtnFacturar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PDDocument documento = new PDDocument();
				PDPage page = new PDPage();
				documento.addPage(page);

				PDPageContentStream contenido;
				try {
					contenido = new PDPageContentStream(documento, page);


					contenido.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);

					
					contenido.beginText();
					contenido.newLineAtOffset(30, 590);
					contenido.showText("Nombre de la empresa");
					contenido.newLineAtOffset(0, -15);
					contenido.showText("Dirección de la empresa");
					contenido.newLineAtOffset(0, -15);
					contenido.showText("Teléfono: +34 123456789");
					contenido.endText();

					
					contenido.moveTo(20, 610);
					contenido.lineTo(250, 610);
					contenido.lineTo(250, 550);
					contenido.lineTo(20, 550);
					contenido.closeAndStroke();

					
					contenido.beginText();
					contenido.newLineAtOffset(280, 590);
					contenido.showText("Nombre del cliente: " + "Pacosss Paco");
					contenido.endText();

					
					contenido.moveTo(270, 610);
					contenido.lineTo(500, 610);
					contenido.lineTo(500, 550);
					contenido.lineTo(270, 550);
					contenido.closeAndStroke();

					
					float yStart = 500;
					

					
					contenido.beginText();
					contenido.newLineAtOffset(20, yStart);
					contenido.showText("Descripción");
					contenido.newLineAtOffset(200, 0);
					contenido.showText("Precio unitario");
					contenido.newLineAtOffset(100, 0);
					contenido.showText("Cantidad");
					contenido.newLineAtOffset(170, 0);
					contenido.showText("Total");
					contenido.endText();

					
					contenido.moveTo(20, yStart - 10);
					contenido.lineTo(520, yStart - 10);
					contenido.stroke();
					DecimalFormat df = new DecimalFormat("#.00");
					Double subtotal = 0.00;
					for (int i = 0; i < gestion.getCarro().size(); i++) {
						yStart -= 40;
						contenido.beginText();
						contenido.newLineAtOffset(20, yStart);
						contenido.showText(gestion.getCarro().get(i).getNombre());
						contenido.newLineAtOffset(230, 0);
						contenido.showText(df.format(gestion.getCarro().get(i).getPrecio()/1.21) + "€");
						contenido.newLineAtOffset(100, 0);
						contenido.showText(gestion.getCarro().get(i).getEnCarro() + "");
						contenido.newLineAtOffset(110, 0);
						contenido.showText(df.format(gestion.getCarro().get(i).getPrecio()/1.21 * gestion.getCarro().get(i).getEnCarro()) + "€");
						contenido.endText();
						subtotal += gestion.getCarro().get(i).getPrecio()/1.21 * gestion.getCarro().get(i).getEnCarro();
						
						contenido.moveTo(20, yStart - 10);
						contenido.lineTo(520, yStart - 10);
						contenido.stroke();
					}

					
					
					double iva = subtotal * 0.21;
					double total = subtotal + iva;

					yStart -= 40;
					contenido.beginText();
					contenido.newLineAtOffset(20, yStart);
					contenido.showText("Subtotal:");
					contenido.newLineAtOffset(230, 0);
					contenido.showText(df.format(subtotal) + "€");
					contenido.newLineAtOffset(180, -15);
					contenido.showText("IVA:");
					contenido.newLineAtOffset(40, 0);
					contenido.showText(df.format(iva) + "€");
					contenido.newLineAtOffset(-40, -15);
					contenido.showText("Total:");
					contenido.newLineAtOffset(40, 0);
					contenido.showText(df.format(total) + "€");
					contenido.endText();

					
					contenido.close();

					
					documento.save("factura.pdf");

					
					String os = System.getProperty("os.name").toLowerCase();
					System.out.println(gestion.getUsuarioDni() + " " +gestion.getNumPedido() );
					gestion.getLogin().getBBDD().insertFactura(gestion.getUsuarioDni(), gestion.getNumPedido());
					
					if (os.contains("win")) {
						Runtime.getRuntime().exec("cmd /c start factura.pdf");
					} else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
						Runtime.getRuntime().exec("open factura.pdf");
					} else {
						
						System.out.println("Este sistema operativo no es compatible para abrir archivos automáticamente.");
					}
			        PrinterJob job = PrinterJob.getPrinterJob();
			        job.setPageable(new PDFPageable(documento));

			        if (job.printDialog()) {
			            try {
							job.print();
						} catch (PrinterException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        }
				} catch (IOException e1) {
					
					//e1.printStackTrace();
				}
			}
		});


        for (int i = 0; i < gestion.getBotonesMenu().size(); i++) {
            gestion.getBotonesMenu().get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                	if (!gestion.isMiniaturas()) {
                		gestion.getContentPane().remove(gestion.getPanelProducto());
        				gestion.getContentPane().add(gestion.getMainPanel());
        				gestion.getContentPane().revalidate();
        				gestion.getContentPane().repaint();
        				gestion.getContentPane().revalidate();
        				gestion.getMainPanel().repaint();
                	}
                	gestion.getMainPanel().setPaginaActual(0);
					gestion.getMainPanel().rellenarArticulos(gestion.getBotonesMenu().indexOf(e.getSource()));
                    gestion.getMainPanel().rellenarTarjetas();
                    gestion.getMainPanel().getTitleLabel().setText(gestion.getItems().get(gestion.getBotonesMenu().indexOf(e.getSource())));
                    gestion.setMiniaturas(true);
                }
            });
        }

    }
}
