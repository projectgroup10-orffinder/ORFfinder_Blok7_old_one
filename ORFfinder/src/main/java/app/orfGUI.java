package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

public class orfGUI extends JFrame {

    JFileChooser fileChooser;
    static String fileName;

    private JPanel mainPanel;
    private JLabel fileLabel;
    private JTextField filePath;
    private JTextArea sequenceArea;
    private JLabel nrFoundORFs;
    private JButton browseButton;
    private JButton controlButton;
    private JButton analyseButton;
    private JButton exportORFButton;
    private JButton blastButton;
    private JLabel nrFoundProteins;
    private JButton exportProteinsButton;
    private JLabel startLabel;
    private JRadioButton atgButton;
    private JRadioButton stopButton;
    private JLabel sequenceLabel;
    private JLabel headerLabel;
    private JTextArea headerArea;
    private JScrollPane scrollSequence;
    private JComboBox chooseStartCodon;
    private JLabel nrORFs;
    private JScrollPane scrollHeader;
    private JTable resultTable;
    private JScrollPane scrollTable;

    public orfGUI () {
        this.setContentPane(mainPanel);

        //button for uploading a file by the user
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileName = "C:\\Users\\sschr\\OneDrive\\Documenten\\Bl7_informaticaproject\\ORFfinder\\testDNATjeerd.fa";
//                fileChooser = new JFileChooser();
//                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//
//                int returnVal = fileChooser.showOpenDialog(orfGUI.this);
//
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    filePath.setText(fileChooser.getSelectedFile().toString());
//                    fileName = filePath.getText();
//                }

            }
        });

        this.setContentPane(mainPanel);
        sequenceArea.setRows(3);
        //sequenceArea.setColumns();
        headerArea.setLineWrap(true);
        headerArea.setWrapStyleWord(true);
        //sequenceArea.setColumns(20);
        sequenceArea.setRows(10);
        sequenceArea.setLineWrap(true);
        sequenceArea.setWrapStyleWord(true);

        controlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                headerArea.setText(ORFfinder.controlFormat(fileName));

                sequenceArea.setText(ORFfinder.getSeq(fileName));


            }
        });
        this.setContentPane(mainPanel);

        chooseStartCodon.addItem("ATG");
        chooseStartCodon.addItem("STOP");

        chooseStartCodon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JComboBox chooseStartCodon = (JComboBox) e.getSource();
                String selectedStart = (String) chooseStartCodon.getSelectedItem();
                chooseStartCodon.setSelectedItem(selectedStart);


            }
        });
        this.setContentPane(mainPanel);

        analyseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStart = (String) chooseStartCodon.getSelectedItem();
                System.out.println(selectedStart);
                HashMap<Integer, ArrayList<String>> resultsMap = ORFfinder.analyse(selectedStart=="ATG");
                //System.out.println(resultsMap);
                nrFoundORFs.setText("Number of found ORF's: "+ Integer.toString(resultsMap.size()));

                String[] columnNames = {"Start position", "Stop position", "DNA sequence", "Aminoacid sequence"};

                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                tableModel.addRow(columnNames);
                resultTable.setSize(1000,600);

                System.out.println(resultsMap);

                for(Object orfObj : resultsMap.values()) {
                    String ORF = String.valueOf(orfObj);
                    String regex = "[\\[,\\]]";
                    String[] columns = ORF.split(regex);

                    tableModel.addRow(new Object[] {columns[1], columns[2], columns[3], columns[4]});

                }

                resultTable.setModel(tableModel);
                //scrollTable.add(resultTable);



            }
        });
        this.setContentPane(mainPanel);
        exportORFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ORFfinder.exportORFtoCSV();
            }
        });

        blastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        exportProteinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
