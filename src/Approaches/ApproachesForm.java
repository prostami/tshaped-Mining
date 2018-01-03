package Approaches;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.queryparser.classic.ParseException;

public class ApproachesForm extends javax.swing.JFrame {

    String dataSetName = "";
    LinkedHashMap<String,LinkedHashMap<Integer,Double>> SA__UId_ProbilityRGivenExpertAndSA = new LinkedHashMap<>();
    LinkedHashMap<String,LinkedHashMap<Integer,Double>> SA__UId_EBA = new LinkedHashMap<>();
    LinkedHashMap<Integer,LinkedHashMap<String,Double>> UId__SA_PsaAnde = new LinkedHashMap<>();
    LinkedHashMap<String,LinkedHashMap<Integer,String>> SA__UId_GoldenShapes = new LinkedHashMap<>();
    
    public ApproachesForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CreateFilesPanel = new javax.swing.JPanel();
        dataSetNameLabel = new javax.swing.JLabel();
        LambdaLabel = new javax.swing.JLabel();
        dataSetNameTextField = new javax.swing.JTextField();
        lambdaTextField = new javax.swing.JTextField();
        DBAButton = new javax.swing.JButton();
        EBAButton = new javax.swing.JButton();
        XEBAButton = new javax.swing.JButton();
        RelevanceProbilityButton = new javax.swing.JButton();
        PerformancePanel = new javax.swing.JPanel();
        NDCGButton = new javax.swing.JButton();
        MRRButton = new javax.swing.JButton();
        SelectApproachComboBox = new javax.swing.JComboBox<>();
        ERRButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        CreateFilesPanel.setBackground(new java.awt.Color(255, 255, 255));

        dataSetNameLabel.setText(" dataSet name: ");
        dataSetNameLabel.setName(""); // NOI18N

        LambdaLabel.setText(" lambda : ");
        LambdaLabel.setName(""); // NOI18N

        dataSetNameTextField.setName(""); // NOI18N

        lambdaTextField.setName(""); // NOI18N

        DBAButton.setText("DBA");
        DBAButton.setEnabled(false);
        DBAButton.setName(""); // NOI18N
        DBAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBAButtonActionPerformed(evt);
            }
        });

        EBAButton.setText("EBA");
        EBAButton.setEnabled(false);
        EBAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EBAButtonActionPerformed(evt);
            }
        });

        XEBAButton.setText("XEBA");
        XEBAButton.setEnabled(false);
        XEBAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XEBAButtonActionPerformed(evt);
            }
        });

        RelevanceProbilityButton.setText("P( R=1 | e,sa )");
        RelevanceProbilityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RelevanceProbilityButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CreateFilesPanelLayout = new javax.swing.GroupLayout(CreateFilesPanel);
        CreateFilesPanel.setLayout(CreateFilesPanelLayout);
        CreateFilesPanelLayout.setHorizontalGroup(
            CreateFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateFilesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CreateFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CreateFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(XEBAButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RelevanceProbilityButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CreateFilesPanelLayout.createSequentialGroup()
                            .addComponent(DBAButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(EBAButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CreateFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CreateFilesPanelLayout.createSequentialGroup()
                            .addComponent(dataSetNameLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dataSetNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(CreateFilesPanelLayout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addComponent(LambdaLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lambdaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CreateFilesPanelLayout.setVerticalGroup(
            CreateFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateFilesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CreateFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataSetNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataSetNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CreateFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LambdaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lambdaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RelevanceProbilityButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CreateFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DBAButton)
                    .addComponent(EBAButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(XEBAButton)
                .addContainerGap())
        );

        PerformancePanel.setBackground(new java.awt.Color(255, 255, 255));

        NDCGButton.setText("NDCG");
        NDCGButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NDCGButtonActionPerformed(evt);
            }
        });

        MRRButton.setText("MRR");
        MRRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRRButtonActionPerformed(evt);
            }
        });

        SelectApproachComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DBA", "EBA", "XEBA" }));

        ERRButton.setText("ERR");
        ERRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ERRButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PerformancePanelLayout = new javax.swing.GroupLayout(PerformancePanel);
        PerformancePanel.setLayout(PerformancePanelLayout);
        PerformancePanelLayout.setHorizontalGroup(
            PerformancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PerformancePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SelectApproachComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(PerformancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ERRButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MRRButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NDCGButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PerformancePanelLayout.setVerticalGroup(
            PerformancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PerformancePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PerformancePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NDCGButton)
                    .addComponent(SelectApproachComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MRRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ERRButton)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CreateFilesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PerformancePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CreateFilesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PerformancePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DBAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBAButtonActionPerformed
        try {
            String DBAFolderPath = getPath("DBA");
            System.out.println("DBA Started!");
            DBA D = new DBA(DBAFolderPath, SA__UId_ProbilityRGivenExpertAndSA, SA__UId_GoldenShapes);
            System.out.println("DBA Finished!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DBAButtonActionPerformed

    private void NDCGButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NDCGButtonActionPerformed
        try{
            dataSetName = dataSetNameTextField.getText();
            String selectApproach = SelectApproachComboBox.getSelectedItem().toString();
            String ApproachPath = getPath(selectApproach);
            String skillAreasPath  = getPath("Clustering");
            String skillShapesXMLPath = getPath("SkillShapesXML");
            NDCG ndcg = new NDCG(ApproachPath, skillAreasPath, skillShapesXMLPath);
            System.out.println("NDCG was Calculated!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_NDCGButtonActionPerformed

    private void MRRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRRButtonActionPerformed
        try {
            dataSetName = dataSetNameTextField.getText();
            String selectApproach = SelectApproachComboBox.getSelectedItem().toString();
            String ApproachPath = getPath(selectApproach);
            String skillAreasPath  = getPath("Clustering");
            MRR mrr = new MRR(ApproachPath, skillAreasPath);
            System.out.println("MRR was Calculated!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MRRButtonActionPerformed

    private void EBAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EBAButtonActionPerformed
        try {
            String EBAFolderPath = getPath("EBA");
            String answerIndexPath = getPath("IndexAnswers");
            String questionIndexPath = getPath("IndexQuestions");
            String skillAreasPath  = getPath("Clustering");
            System.out.println("EBA Started!");
            EBA E = new EBA(EBAFolderPath, SA__UId_ProbilityRGivenExpertAndSA, SA__UId_GoldenShapes, skillAreasPath, questionIndexPath, answerIndexPath);
            System.out.println("EBA Finished!");
            SA__UId_EBA = E.getProbilityTAndRList();
            UId__SA_PsaAnde = E.getPsaAndeList();
            SA__UId_ProbilityRGivenExpertAndSA.clear();
            XEBAButton.setEnabled(true);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EBAButtonActionPerformed

    private void XEBAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XEBAButtonActionPerformed
        try {
            String XEBAFolderPath = getPath("XEBA");
            System.out.println("XEBA Started!");
            XEBA X = new XEBA(XEBAFolderPath, UId__SA_PsaAnde, SA__UId_GoldenShapes, SA__UId_EBA);
            System.out.println("XEBA Finished!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_XEBAButtonActionPerformed

    private void ERRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ERRButtonActionPerformed
        try{
            String selectedApproach = SelectApproachComboBox.getSelectedItem().toString();
            String ApproachPath = getPath(selectedApproach);
            String skillAreasPath  = getPath("Clustering");
            ERR err = new ERR(ApproachPath, skillAreasPath);
            System.out.println("ERR was Calculated!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ERRButtonActionPerformed

    private void RelevanceProbilityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RelevanceProbilityButtonActionPerformed
        try {
            dataSetName = dataSetNameTextField.getText();
            String lambda = lambdaTextField.getText();
            String answerIndexPath = getPath("IndexAnswers");
            String skillAreasPath  = getPath("Clustering");
            String skillShapesXMLPath = getPath("SkillShapesXML");
            AllRelevanceProbility R = new AllRelevanceProbility(answerIndexPath ,dataSetName,lambda, skillAreasPath);
            SA__UId_ProbilityRGivenExpertAndSA = R.getProbilitiesList();
            GoldenUsersShapes G = new GoldenUsersShapes(skillAreasPath, skillShapesXMLPath);
            SA__UId_GoldenShapes = G.getGoldenUsersShapesList();
            RelevanceProbilityButton.setEnabled(false);
            DBAButton.setEnabled(true);
            EBAButton.setEnabled(true);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(ApproachesForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_RelevanceProbilityButtonActionPerformed
    
    private String getPath(String fileName) {
        return Path.FilePaths.getFilePaths(dataSetName, fileName);
    }
        
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApproachesForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApproachesForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CreateFilesPanel;
    private javax.swing.JButton DBAButton;
    private javax.swing.JButton EBAButton;
    private javax.swing.JButton ERRButton;
    private javax.swing.JLabel LambdaLabel;
    private javax.swing.JButton MRRButton;
    private javax.swing.JButton NDCGButton;
    private javax.swing.JPanel PerformancePanel;
    private javax.swing.JButton RelevanceProbilityButton;
    private javax.swing.JComboBox<String> SelectApproachComboBox;
    private javax.swing.JButton XEBAButton;
    private javax.swing.JLabel dataSetNameLabel;
    private javax.swing.JTextField dataSetNameTextField;
    private javax.swing.JTextField lambdaTextField;
    // End of variables declaration//GEN-END:variables

}
