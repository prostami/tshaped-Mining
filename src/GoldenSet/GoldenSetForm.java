package GoldenSet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoldenSetForm extends javax.swing.JFrame {

    String dataSetName = "";
    public GoldenSetForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        dataSetNameLabel = new javax.swing.JLabel();
        dataSetNameTextField = new javax.swing.JTextField();
        CreateQuestionListButton = new javax.swing.JButton();
        CreateAnswerListButton = new javax.swing.JButton();
        FmeasurePanel = new javax.swing.JPanel();
        BinsCountLabel = new javax.swing.JLabel();
        BinsCountTextField = new javax.swing.JTextField();
        AlphaLabel = new javax.swing.JLabel();
        alphaTextField = new javax.swing.JTextField();
        FmeasureLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        knowledgeLevelLabel = new javax.swing.JLabel();
        AdvancedTextField = new javax.swing.JTextField();
        AdvancedLabel = new javax.swing.JLabel();
        IntermediateLabel = new javax.swing.JLabel();
        IntermediateTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        RunButton = new javax.swing.JButton();

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dataSetNameLabel.setText("dataSet Name : ");

        CreateQuestionListButton.setText("Create Question List");
        CreateQuestionListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateQuestionListButtonActionPerformed(evt);
            }
        });

        CreateAnswerListButton.setText("Create Answer List");
        CreateAnswerListButton.setEnabled(false);
        CreateAnswerListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateAnswerListButtonActionPerformed(evt);
            }
        });

        FmeasurePanel.setBackground(new java.awt.Color(255, 255, 255));

        BinsCountLabel.setText("Bins Count :");

        BinsCountTextField.setEnabled(false);

        AlphaLabel.setText("Alpha :");

        alphaTextField.setEnabled(false);

        FmeasureLabel.setText("Fmeasure");

        knowledgeLevelLabel.setText("Knowledge Level");

        AdvancedTextField.setEnabled(false);

        AdvancedLabel.setText("highest bins of Advanced Level :");

        IntermediateLabel.setText("highest bins of Intermediate Level :");

        IntermediateTextField.setEnabled(false);

        jLabel1.setText("%");

        javax.swing.GroupLayout FmeasurePanelLayout = new javax.swing.GroupLayout(FmeasurePanel);
        FmeasurePanel.setLayout(FmeasurePanelLayout);
        FmeasurePanelLayout.setHorizontalGroup(
            FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(FmeasurePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IntermediateLabel)
                    .addComponent(AdvancedLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(IntermediateTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(AdvancedTextField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(FmeasurePanelLayout.createSequentialGroup()
                .addGroup(FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FmeasureLabel)
                    .addComponent(knowledgeLevelLabel))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FmeasurePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BinsCountLabel)
                    .addComponent(AlphaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FmeasurePanelLayout.createSequentialGroup()
                        .addComponent(alphaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addComponent(BinsCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );
        FmeasurePanelLayout.setVerticalGroup(
            FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FmeasurePanelLayout.createSequentialGroup()
                .addComponent(FmeasureLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BinsCountLabel)
                    .addComponent(BinsCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AlphaLabel)
                    .addComponent(alphaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(knowledgeLevelLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AdvancedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdvancedLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FmeasurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IntermediateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IntermediateLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        RunButton.setText("Run");
        RunButton.setEnabled(false);
        RunButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FmeasurePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RunButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CreateQuestionListButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CreateAnswerListButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 35, Short.MAX_VALUE)
                        .addComponent(dataSetNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dataSetNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataSetNameLabel)
                    .addComponent(dataSetNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CreateQuestionListButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CreateAnswerListButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FmeasurePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RunButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CreateQuestionListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateQuestionListButtonActionPerformed
        try {
            dataSetName = dataSetNameTextField.getText();
            String postFileAddress     = getPath("Posts");
            String SkillAreasPath      = getPath("Clustering");
            String QuestionDataSetPath = getPath("QuestionDataSet"); 
            QuestionsDataSet Q = new QuestionsDataSet(dataSetName,postFileAddress,SkillAreasPath,QuestionDataSetPath);
            CreateAnswerListButton.setEnabled(true);
            CreateQuestionListButton.setEnabled(false);
            dataSetNameTextField.setEnabled(false);
            dataSetNameLabel.setEnabled(false);
        } catch (IOException ex) {
            Logger.getLogger(GoldenSetForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_CreateQuestionListButtonActionPerformed

    private void CreateAnswerListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateAnswerListButtonActionPerformed
        try {
            String postFileAddress     = getPath("Posts");
            String QuestionDataSetPath = getPath("QuestionDataSet");
            String AnswerDataSetPath   = getPath("AnswerDataSet");
            AnswersDataSet Q = new AnswersDataSet(dataSetName,postFileAddress,QuestionDataSetPath,AnswerDataSetPath);
            CreateAnswerListButton.setEnabled(false);
            BinsCountTextField.setEnabled(true);
            alphaTextField.setEnabled(true);
            AdvancedTextField.setEnabled(true);
            IntermediateTextField.setEnabled(true);
            RunButton.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(GoldenSetForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_CreateAnswerListButtonActionPerformed

    private void RunButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunButtonActionPerformed
        try {
            String AnswerDataSetPath        = getPath("AnswerDataSet");
            String userPerformanceXMLPath   = getPath("userPerformance");
            String expertiseShapesXMLPath   = getPath("SkillShapesXML");
            String expertiseShapeCSVPath    = getPath("Shapes");
            String skillAreasCSVPath        = getPath("skillAreasProperty");
            String AdvancedLevelCSVPath     = getPath("AdvancedLevel");
            String IntermediateLevelCSVPath = getPath("IntermediateLevel");
            String BeginnerLevelCSVPath     = getPath("BeginnerLevel");
            int binsCount = Integer.parseInt(BinsCountTextField.getText());
            double alpha  = Double.parseDouble(alphaTextField.getText());
            int AdvancedMeter = Integer.parseInt(AdvancedTextField.getText());
            int IntermediateMeter = Integer.parseInt(IntermediateTextField.getText());
            KnowledgeLevel KL = new KnowledgeLevel(dataSetName,AnswerDataSetPath,userPerformanceXMLPath,expertiseShapesXMLPath,expertiseShapeCSVPath,skillAreasCSVPath,AdvancedLevelCSVPath,IntermediateLevelCSVPath,BeginnerLevelCSVPath,binsCount,alpha,AdvancedMeter,IntermediateMeter);
        } catch (IOException ex) {
            Logger.getLogger(GoldenSetForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_RunButtonActionPerformed

    private String getPath(String fileName) {
        return Path.FilePaths.getFilePaths(dataSetName, fileName);
    }
        
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GoldenSetForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GoldenSetForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GoldenSetForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GoldenSetForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GoldenSetForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdvancedLabel;
    private javax.swing.JTextField AdvancedTextField;
    private javax.swing.JLabel AlphaLabel;
    private javax.swing.JLabel BinsCountLabel;
    private javax.swing.JTextField BinsCountTextField;
    private javax.swing.JButton CreateAnswerListButton;
    private javax.swing.JButton CreateQuestionListButton;
    private javax.swing.JLabel FmeasureLabel;
    private javax.swing.JPanel FmeasurePanel;
    private javax.swing.JLabel IntermediateLabel;
    private javax.swing.JTextField IntermediateTextField;
    private javax.swing.JButton RunButton;
    private javax.swing.JTextField alphaTextField;
    private javax.swing.JLabel dataSetNameLabel;
    private javax.swing.JTextField dataSetNameTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel knowledgeLevelLabel;
    // End of variables declaration//GEN-END:variables
}
