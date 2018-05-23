package Vista;

import Controlador.ControladorGráfico;
import Controlador.SeleniumController;
import Modelo.ConnMySQL;
import Modelo.GestorBD;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import util.CustomOutputStream;
import util.TableTransformer;




public class Frm_Lanzador extends javax.swing.JFrame {

    private PrintStream standarOut;
    String PATH;
    Thread ejecucion = new Thread();
    GestorBD gestorBD = new GestorBD();
    ConnMySQL conexion = new ConnMySQL();
    ControladorGráfico controlador = new ControladorGráfico();
    int ESTADO =0;

    public Frm_Lanzador() {
        initComponents();
        this.cmb_Modulo.setModel(controlador.cargarComboModulo());
        //Se obtiene el valor seleccionado de combo Módulo, con el valor obtenido se obtiene los
        //registros dependientes del combo Producto
        this.cmb_Producto.setModel(controlador.cargarComboProducto(this.cmb_Modulo.getSelectedItem().toString()));

        //Se añade un LISTENER para que a cada cambio del combo Módulo
        //se realiza una nueva búsqueda en la tabla functional_scene
        cmb_Modulo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // los nuevos registros son agregados al combo Producto
                    Object item = e.getItem();
                    cmb_Producto.setModel(controlador.cargarComboProducto(item.toString()));
                }
            }
        });

        cargaComboTest();
        groupButton();

        DefaultListModel mod = new DefaultListModel();
        lst_Casos.setModel(mod);
        lst_Casos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        PrintStream printStream = new PrintStream(new CustomOutputStream(txt_Log));
        standarOut = System.out;
        System.setOut(printStream);
        System.setErr(printStream);
        txt_Log.setEditable(false);
        Panel_txtTest_lstCasos.setVisible(false);
        //rdb_Producto.setVisible(false);
        //rdb_Test.setVisible(false);
    }


    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cmb_Producto = new javax.swing.JComboBox<>();
        btn_Ejecutar = new javax.swing.JButton();
        txt_Path = new javax.swing.JTextField();
        btnReporte = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmb_Modulo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmb_TestCase = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Panel_txtTest_lstCasos = new javax.swing.JPanel();
        txt_Test = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        lst_Casos = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        rdb_Producto = new javax.swing.JRadioButton();
        rdb_Test = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_Log = new javax.swing.JTextArea();
        btnLimpiar = new javax.swing.JButton();
        btnDetener = new javax.swing.JButton();
        btn_pausa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_Resultado = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pruebas Automatizadas - QA");
        setLocationByPlatform(true);
        setResizable(false);

        cmb_Producto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cmb_Producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmb_ProductoMouseClicked(evt);
            }
        });

        btn_Ejecutar.setText("Iniciar test");
        btn_Ejecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EjecutarActionPerformed(evt);
            }
        });

        txt_Path.setEditable(false);
        txt_Path.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt_PathMousePressed(evt);
            }
        });
        txt_Path.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PathActionPerformed(evt);
            }
        });

        btnReporte.setText("Reporte de errores");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        jLabel1.setText("Guardar resultado de test en:");

        cmb_Modulo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Seleccione módulo:");

        cmb_TestCase.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_TestCase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmb_TestCaseMouseClicked(evt);
            }
        });
        cmb_TestCase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_TestCaseActionPerformed(evt);
            }
        });

        jLabel3.setText("Universo a procesar:");

        jLabel4.setText("Ejecutar test case:");

        txt_Test.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TestActionPerformed(evt);
            }
        });
        txt_Test.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_TestKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_TestKeyReleased(evt);
            }
        });

        lst_Casos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lst_Casos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lst_CasosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(lst_Casos);

        jLabel5.setText("Buscar por:");

        javax.swing.GroupLayout Panel_txtTest_lstCasosLayout = new javax.swing.GroupLayout(Panel_txtTest_lstCasos);
        Panel_txtTest_lstCasos.setLayout(Panel_txtTest_lstCasosLayout);
        Panel_txtTest_lstCasosLayout.setHorizontalGroup(
            Panel_txtTest_lstCasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_txtTest_lstCasosLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(Panel_txtTest_lstCasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_txtTest_lstCasosLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Test, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(170, Short.MAX_VALUE))
        );
        Panel_txtTest_lstCasosLayout.setVerticalGroup(
            Panel_txtTest_lstCasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_txtTest_lstCasosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(Panel_txtTest_lstCasosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_Test, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(txt_Path, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addComponent(btn_Ejecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnReporte)
                .addGap(104, 104, 104))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rdb_Producto)
                            .addComponent(rdb_Test))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_Modulo, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_TestCase, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_Producto, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(Panel_txtTest_lstCasos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdb_Producto)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(cmb_Producto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cmb_TestCase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(rdb_Test))
                        .addGap(70, 70, 70)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(Panel_txtTest_lstCasos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cmb_Modulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Ejecutar)
                    .addComponent(btnReporte)))
        );

        txt_Log.setColumns(20);
        txt_Log.setRows(5);
        jScrollPane1.setViewportView(txt_Log);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnDetener.setText("Detener");
        btnDetener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetenerActionPerformed(evt);
            }
        });

        btn_pausa.setText("Pausa");
        btn_pausa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pausaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 955, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDetener, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(btn_pausa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btn_pausa)
                        .addGap(38, 38, 38)
                        .addComponent(btnDetener)
                        .addGap(0, 110, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        tbl_Resultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Módulo", "Test", "Descripción", "Resultado", "Observaciones", "Fecha", "Tiempo de ejecución"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_Resultado);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_PathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PathActionPerformed
       
    }//GEN-LAST:event_txt_PathActionPerformed
        
    private void txt_PathMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_PathMousePressed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File("C:\\Users\\manuelfvalles\\Documents\\Evidencia"));
        int resultado = fc.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fc.getSelectedFile();
            PATH = archivo.getAbsolutePath();
            txt_Path.setText(PATH);
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una carpeta");
        }//Indica la ruta para guardar evedencia
    }//GEN-LAST:event_txt_PathMousePressed

    private void btn_EjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EjecutarActionPerformed
        //Botón iniciar test
        if (ejecucion.isAlive()) {
            JOptionPane.showMessageDialog(null, "Debe esperar a que finalice el proceso");
        } else if (PATH == null) {
            JOptionPane.showMessageDialog(null, "Debes ingresar la ruta de las evidencias!!");
        } else {
            try {
                int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de empezar?", "Mensaje", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    String prueba, pruebas;
                    if (rdb_Producto.isSelected()) {
                        pruebas = (String) cmb_Producto.getSelectedItem();
                        ejecucion = getEjecucion(pruebas);
                        ejecucion.start();
                    } else if (rdb_Test.isSelected()) {
                        prueba = txt_Test.getText();
                        ejecucion = getEjecucion1(prueba);
                        ejecucion.start();
                    } else {
                    }
                }
            } catch (Exception ex) {
                System.err.println(" " + ex);
            }
        }//Funcionalidad de botón iniciar
    }//GEN-LAST:event_btn_EjecutarActionPerformed

    private void txt_TestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TestActionPerformed
        
    }//GEN-LAST:event_txt_TestActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txt_Log.setText(""); //Limpia el campo de log.
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnDetenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetenerActionPerformed
        if (ejecucion.isAlive()) {
            System.out.println("=================PARANDO EJECUCION==========");
            ejecucion.stop();
            crearLog();
        } else {
            JOptionPane.showMessageDialog(null, "El proceso se encuentra detenido");
        }//Funcionalidad del botón detener.
    }//GEN-LAST:event_btnDetenerActionPerformed

    private void txt_TestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_TestKeyPressed
        
    }//GEN-LAST:event_txt_TestKeyPressed

    private void txt_TestKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_TestKeyReleased
        //Llena la lista para ejecutar un test en específico        
        String caso = txt_Test.getText();        
        lst_Casos.removeAll();
        
        DefaultListModel lista = new DefaultListModel();
        String[] casos =new GestorBD().getTestPorNombre(caso);
        for (int i = 0; i < casos.length; i++) {
            lista.addElement(casos[i]);    
        }
        lst_Casos.setModel(lista);        
    }//GEN-LAST:event_txt_TestKeyReleased

    private void lst_CasosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lst_CasosMouseClicked
        //Selecciona el test a ejecutar
        txt_Test.setText(lst_Casos.getSelectedValue());
    }//GEN-LAST:event_lst_CasosMouseClicked

    private void btn_pausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pausaActionPerformed
        //botón pausar
        if (ejecucion.isAlive() && ESTADO == 0) {
            System.out.println("=================PAUSANDO EJECUCION==========");
            ejecucion.suspend();
            ESTADO = 1;
            btn_pausa.setText("Play |>");
            crearLog();
        } else {
            ejecucion.resume();
            btn_pausa.setText("Pausa");
            ESTADO = 0;
            System.out.println("=================REANUDANDO EJECUCION==========");
        }
    }//GEN-LAST:event_btn_pausaActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        try {

            Map parameters = new HashMap();
            parameters.put("Modulo", new java.lang.String());
            parameters.put("Test", new java.lang.String());
            parameters.put("Observaciones", new java.lang.String());
            parameters.put("Fecha", new java.util.Date());
            parameters.put("Tiempo_de_ejecucion", new java.util.Date());

            JasperReport report = JasperCompileManager.compileReport("src/Vista/reporte.jrxml");

            JasperPrint print = JasperFillManager.fillReport(report, parameters, ConnMySQL.getInstancia().getConexion());
            // Exporta el informe a PDF
            JasperExportManager.exportReportToPdfFile(print, "C:\\Users\\manuelfvalles\\Documents\\Log\\Reporte.pdf");
            //Para visualizar el pdf directamente desde java
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(Frm_Lanzador.class.getName()).log(Level.SEVERE, null, ex);
        }//Reporte
    }//GEN-LAST:event_btnReporteActionPerformed

    private void cmb_TestCaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_TestCaseActionPerformed
        //Oculta y/o muestra los campos de test
        int testcase = (int) cmb_TestCase.getSelectedIndex() + 1;
        if (testcase == 2) {
            Panel_txtTest_lstCasos.setVisible(true);
        } else {
            Panel_txtTest_lstCasos.setVisible(false);
        }
    }//GEN-LAST:event_cmb_TestCaseActionPerformed

    private void cmb_TestCaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmb_TestCaseMouseClicked
        rdb_Test.setSelected(true); //Selecciona radio button Test
    }//GEN-LAST:event_cmb_TestCaseMouseClicked

    private void cmb_ProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmb_ProductoMouseClicked
        rdb_Producto.setSelected(true); //Selecciona radio button Producto
    }//GEN-LAST:event_cmb_ProductoMouseClicked
    
    private void cargaComboTest() {
        cmb_TestCase.removeAllItems();
        ArrayList<String> ejecucion = new ArrayList<>();
        ejecucion = new ControladorGráfico().getEjecutar();
        for (int i = 0; i < ejecucion.size(); i++) {
            cmb_TestCase.addItem(ejecucion.get(i));
        }
    }//Llena el combo ejecutar test case
    
    private void groupButton() {
        ButtonGroup rdb = new ButtonGroup();
        rdb.add(rdb_Producto);
        rdb.add(rdb_Test);
    }//Agrupa los radio button, los mismo se encuentran no visibles.
    
    private Thread getEjecucion(final String indice) {    
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SeleniumController selenium = new SeleniumController();
                    String cadena = indice;
                    int VI = cadena.indexOf("web");
                    if (VI != -1) {
                        selenium.setUpWeb(PATH);
                        selenium.producto(indice);
                        crearLog();
                        llenarTabla();
                    } else {
                        selenium.setUp(PATH);
                        selenium.producto(indice);
                        crearLog();
                        llenarTabla();
                    }
                } catch (Exception ex) {
                    System.err.println("Ha ocurrido un error al ejecutar pruebas producto: " + ex);
                    crearLog();
                }
            }
        });
        return hilo;
    }//Método que permite la ejecución de un universo de test case.
    
    private Thread getEjecucion1(final String nomPrueba) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SeleniumController selenium = new SeleniumController();
                    String cadena = nomPrueba;
                    int VI = cadena.indexOf("web");
                    if (VI != -1) {
                        selenium.setUpWeb(PATH);
                    } else {
                        selenium.setUp(PATH);
                    }
                    selenium.test(nomPrueba);
                    crearLog();
                    llenarTabla();
                } catch (Exception ex) {
                    System.err.println("Ha ocurrido un error al ejecutar prueba: " + ex);
                    crearLog();
                }
            }
        });
        return hilo;
    }//Método que ejecuta un test case específico.

    private void llenarTabla(){
        ResultSet rs = gestorBD.getResultado();
        DefaultTableModel modelo = new DefaultTableModel(TableTransformer.getDataMultiDimensional(rs), TableTransformer.getColumnas(rs));       
        tbl_Resultado.setModel(modelo);
    }//Llena la tabla resultado
    
    public void crearLog() {
        try {
            File archivo = new File(PATH + "/Log.txt");
            FileWriter fw = new FileWriter(archivo);
            BufferedWriter escritor = new BufferedWriter(fw);
            escritor.write(txt_Log.getText());
            escritor.close();
            fw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al crear archivo de log : " + ex.getCause());
        }
    }//Genera log en formato txt
       
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_Lanzador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Lanzador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Lanzador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Lanzador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_Lanzador().setVisible(true);                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_txtTest_lstCasos;
    private javax.swing.JButton btnDetener;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btn_Ejecutar;
    private javax.swing.JButton btn_pausa;
    private javax.swing.JComboBox<String> cmb_Modulo;
    private javax.swing.JComboBox<String> cmb_Producto;
    private javax.swing.JComboBox<String> cmb_TestCase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> lst_Casos;
    private javax.swing.JRadioButton rdb_Producto;
    private javax.swing.JRadioButton rdb_Test;
    private javax.swing.JTable tbl_Resultado;
    private javax.swing.JTextArea txt_Log;
    private javax.swing.JTextField txt_Path;
    private javax.swing.JTextField txt_Test;
    // End of variables declaration//GEN-END:variables
}