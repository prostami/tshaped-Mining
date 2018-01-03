package TopFrequentTags;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrequentTagsForm extends javax.swing.JFrame {
    LinkedHashMap<String,Integer> topFrequentTagsList = new LinkedHashMap<>();
    String dataSetName= "";
    
    public FrequentTagsForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        initialPanel = new javax.swing.JPanel();
        dataSetLabel = new javax.swing.JLabel();
        dataSetTextField = new javax.swing.JTextField();
        tagsCountTextField = new javax.swing.JTextField();
        tagsCountLabel = new javax.swing.JLabel();
        similarityPanel = new javax.swing.JPanel();
        similarityLabel = new javax.swing.JLabel();
        similarityButton = new javax.swing.JButton();
        findAndPrintPanel = new javax.swing.JPanel();
        findTagsButton = new javax.swing.JButton();
        printTagsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        initialPanel.setBackground(new java.awt.Color(255, 255, 255));

        dataSetLabel.setText("dataSet: ");

        tagsCountLabel.setText("#Tags:");

        javax.swing.GroupLayout initialPanelLayout = new javax.swing.GroupLayout(initialPanel);
        initialPanel.setLayout(initialPanelLayout);
        initialPanelLayout.setHorizontalGroup(
            initialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(initialPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(initialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, initialPanelLayout.createSequentialGroup()
                        .addComponent(tagsCountLabel)
                        .addGap(18, 18, 18))
                    .addGroup(initialPanelLayout.createSequentialGroup()
                        .addComponent(dataSetLabel)
                        .addGap(8, 8, 8)))
                .addGroup(initialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dataSetTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                    .addComponent(tagsCountTextField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        initialPanelLayout.setVerticalGroup(
            initialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(initialPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(initialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataSetLabel)
                    .addComponent(dataSetTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(initialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tagsCountLabel)
                    .addComponent(tagsCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        similarityPanel.setBackground(new java.awt.Color(255, 255, 255));

        similarityLabel.setText("similarity: ");

        similarityButton.setText("calculate");
        similarityButton.setEnabled(false);
        similarityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                similarityButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout similarityPanelLayout = new javax.swing.GroupLayout(similarityPanel);
        similarityPanel.setLayout(similarityPanelLayout);
        similarityPanelLayout.setHorizontalGroup(
            similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(similarityPanelLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(similarityLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(similarityButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        similarityPanelLayout.setVerticalGroup(
            similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(similarityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(similarityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(similarityLabel)
                    .addComponent(similarityButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        findAndPrintPanel.setBackground(new java.awt.Color(255, 255, 255));

        findTagsButton.setText("find tags");
        findTagsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findTagsButtonActionPerformed(evt);
            }
        });

        printTagsButton.setText("print tags");
        printTagsButton.setEnabled(false);
        printTagsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printTagsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout findAndPrintPanelLayout = new javax.swing.GroupLayout(findAndPrintPanel);
        findAndPrintPanel.setLayout(findAndPrintPanelLayout);
        findAndPrintPanelLayout.setHorizontalGroup(
            findAndPrintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(findAndPrintPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(findAndPrintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(findTagsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(printTagsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        findAndPrintPanelLayout.setVerticalGroup(
            findAndPrintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, findAndPrintPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(findTagsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(printTagsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(similarityPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(initialPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(findAndPrintPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(findAndPrintPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(initialPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(similarityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void findTagsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findTagsButtonActionPerformed
        try {
            dataSetName = dataSetTextField.getText();
            int tagsCount  = Integer.parseInt(tagsCountTextField.getText());
            String postFileAddress = getPath("Posts");
            FrequentTags FT = new FrequentTags(postFileAddress,dataSetName,tagsCount);
            topFrequentTagsList = FT.getSortedTopFrequentTagsList();
            initialPanel.setEnabled(false);
            findTagsButton.setEnabled(false);
            printTagsButton.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(FrequentTagsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_findTagsButtonActionPerformed

    private void similarityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_similarityButtonActionPerformed
        try {
            String postFileAddress = getPath("Posts");
            String similarityFilePath = getPath("Similarity");
            similarity s = new similarity(dataSetName,postFileAddress,topFrequentTagsList.keySet());
            s.getSimilarity(similarityFilePath);
        } catch (IOException ex) {
            Logger.getLogger(FrequentTagsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_similarityButtonActionPerformed

    private void printTagsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printTagsButtonActionPerformed
        try {
            String frequentTagsFileAddress = getPath("topFrequntTags");
            printTopFrequentTags(frequentTagsFileAddress,dataSetName);
            findAndPrintPanel.setEnabled(false);
            similarityButton.setEnabled(true);
            printTagsButton.setEnabled(false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrequentTagsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_printTagsButtonActionPerformed

    private void printTopFrequentTags(String fileAddress, String dataSetName) throws FileNotFoundException{
        PrintWriter writer = new PrintWriter(fileAddress);
        for(String tag : topFrequentTagsList.keySet()){
            writer.println(tag);
        }
        writer.close();
    }
    
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
            java.util.logging.Logger.getLogger(FrequentTagsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrequentTagsForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dataSetLabel;
    private javax.swing.JTextField dataSetTextField;
    private javax.swing.JPanel findAndPrintPanel;
    private javax.swing.JButton findTagsButton;
    private javax.swing.JPanel initialPanel;
    private javax.swing.JButton printTagsButton;
    private javax.swing.JButton similarityButton;
    private javax.swing.JLabel similarityLabel;
    private javax.swing.JPanel similarityPanel;
    private javax.swing.JLabel tagsCountLabel;
    private javax.swing.JTextField tagsCountTextField;
    // End of variables declaration//GEN-END:variables
}
