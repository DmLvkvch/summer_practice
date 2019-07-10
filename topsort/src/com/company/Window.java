package com.company;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel{
    private Graph graph;
    private TopSort sort;
    private SourceGraphField graphField;
    private SortedGraphField sortedGraphField;
    private LeftControlPanel leftPanel;
    ///////////////////////////////

    public Window(Graph graph, TopSort sort) {
        this.sort = sort;
        this.graph = graph;
        this.graphField = new SourceGraphField(graph, sort);
        //new SourceGraphField(graph);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = 1;
        constraints.gridy   = 0  ;
        constraints.gridx = 0;
        contentPanel.add(graphField, constraints);



        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = 1;
        constraints.gridx     = 0;
        constraints.gridy     = 1;
        sortedGraphField = new SortedGraphField(graph);
        //contentPanel.add(sortedGraphField, constraints);

        JScrollPane jScrollPane = new JScrollPane(sortedGraphField);
        jScrollPane.setPreferredSize(new Dimension(900,150));
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        contentPanel.add(jScrollPane, constraints);


        leftPanel = new LeftControlPanel();
        constraints.weighty   = 0.0;
        constraints.gridx     = 0;
        constraints.gridy     = 2;
        contentPanel.add(leftPanel, constraints);





        //commentsLabel.setPreferredSize(new Dimension(900, 150));


        RigthControlPanel cp  = new RigthControlPanel(this.graph, contentPanel, graphField, sortedGraphField);

        cp.setCommentPane(leftPanel.commentArea);
        constraints.gridx = 1;      // первая ячейка таблицы по горизонтали
        constraints.gridy = 0;
        constraints.gridheight = 3;
        contentPanel.add(cp, constraints);

        this.add(contentPanel);
    }


}