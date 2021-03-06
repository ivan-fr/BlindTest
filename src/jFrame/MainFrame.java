package jFrame;


import models.Fichier;
import models.Reponse;
import models.Theme;
import sockets.ClientHandler;
import sockets.Party;
import sockets.Timer;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/*

0 -> Sign in
1 -> Sign Up
2 -> Session Game
3 -> Create session
4 -> Score

 */


public class MainFrame extends javax.swing.JFrame {


    ClientHandler client;

    public MainFrame(ClientHandler client) {
        this.client = client ;
        initComponents();
        sessionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTabbedPane1.setSelectedIndex(0);
    }

    public void updateThemeTable(List<Theme> themes) {
        DefaultTableModel dm = (DefaultTableModel)themeTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();

        for (Theme theme:
             themes) {
            ((DefaultTableModel) themeTable.getModel()).addRow(new Object[]{theme.getValue(), false});
        }
    }

    public void updateSessionTable(List<Party> parties) {
        DefaultTableModel dm = (DefaultTableModel)sessionTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();

        for (Party p:
                parties) {
            if (p.getCurrentQuestion() == 0 && p.getParticipants().size() < 4) {
                ((DefaultTableModel) sessionTable.getModel()).addRow(new Object[]{p.getAuthorKey(), p.getPartyName(), p.getThemesKey().toString(), p.getFichiersOrder().size()});
            }
        }
    }


    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        signInUsername = new javax.swing.JTextField();
        signInPassword = new javax.swing.JPasswordField();
        signInButton = new javax.swing.JButton();
        redirectToSignUpButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        signUpUsername = new javax.swing.JTextField();
        signUpPassword = new javax.swing.JPasswordField();
        signUpConfirmPassword = new javax.swing.JPasswordField();
        signUpButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        redirectToSignInButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        choice1 = new javax.swing.JButton();
        choice2 = new javax.swing.JButton();
        choice4 = new javax.swing.JButton();
        choice3 = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        user1 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        user2 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        user3 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        user4 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        sessionName = new javax.swing.JTextField();
        createSessionButton = new javax.swing.JButton();
        signOutButton = new javax.swing.JButton();
        connectedUsername = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sessionTable = new javax.swing.JTable();
        joinSessionButton = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        textJoinSession = new javax.swing.JTextPane();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        themeTable = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        score3 = new javax.swing.JLabel();
        leaveButton = new javax.swing.JButton();
        score4 = new javax.swing.JLabel();
        score1 = new javax.swing.JLabel();
        score2 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(0, 0, 0, 80));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sign In");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Username: ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Password: ");

        signInButton.setBackground(new java.awt.Color(1, 199, 71));
        signInButton.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        signInButton.setForeground(new java.awt.Color(255, 255, 255));
        signInButton.setText("Sign In");
        signInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signInButtonActionPerformed(evt);
            }
        });

        redirectToSignUpButton.setBackground(new java.awt.Color(0, 104, 218));
        redirectToSignUpButton.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        redirectToSignUpButton.setForeground(new java.awt.Color(255, 255, 255));
        redirectToSignUpButton.setText("Sign Up");
        redirectToSignUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redirectToSignUpButtonActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("You don't have an account ?");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(signInUsername)
                                                                .addComponent(signInPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addComponent(signInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(99, 99, 99)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(47, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(137, 137, 137))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(redirectToSignUpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(131, 131, 131))))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel1)
                                .addGap(35, 35, 35)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(signInUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(signInPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addComponent(signInButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(redirectToSignUpButton)
                                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 340, 310));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 10, 860, 380));

        jTabbedPane1.addTab("tab1", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(0, 0, 0, 80));
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Sign Up");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Username: ");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Password: ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Confirm Password: ");

        signUpButton.setBackground(new java.awt.Color(0, 104, 218));
        signUpButton.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        signUpButton.setForeground(new java.awt.Color(255, 255, 255));
        signUpButton.setText("Sign Up");
        signUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signUpButtonActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Already have an account ?");

        redirectToSignInButton.setBackground(new java.awt.Color(1, 199, 71));
        redirectToSignInButton.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        redirectToSignInButton.setForeground(new java.awt.Color(255, 255, 255));
        redirectToSignInButton.setText("Sign In");
        redirectToSignInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redirectToSignInButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(170, 170, 170)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addGap(56, 56, 56)
                                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                                                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addGap(37, 37, 37)
                                                                .addComponent(jLabel9)))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(signUpUsername)
                                                        .addComponent(signUpPassword)
                                                        .addComponent(signUpConfirmPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                                        .addComponent(signUpButton)))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(149, 149, 149)
                                                .addComponent(jLabel10)))
                                .addContainerGap(35, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(redirectToSignInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(140, 140, 140))
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel11)
                                .addGap(30, 30, 30)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(signUpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(signUpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(signUpConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addComponent(signUpButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(redirectToSignInButton)
                                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, -1, 320));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.jpg"))); // NOI18N
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 10, 860, 390));

        jTabbedPane1.addTab("tab2", jPanel3);

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(250, 250, 250));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Session Name");
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.jpg"))); // NOI18N
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 50));
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 460, 220));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel20.setText("Who is this ?");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, -1, -1));

        choice1.setBackground(new java.awt.Color(243, 17, 238));
        choice1.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        choice1.setForeground(new java.awt.Color(255, 255, 255));
        choice1.setText("Choix 1");
        choice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice1ActionPerformed(evt);
            }
        });
        jPanel6.add(choice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 120, -1));

        choice2.setBackground(new java.awt.Color(117, 153, 148));
        choice2.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        choice2.setForeground(new java.awt.Color(255, 255, 255));
        choice2.setText("choix 2");
        choice2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice2ActionPerformed(evt);
            }
        });
        jPanel6.add(choice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 370, 130, -1));

        choice4.setBackground(new java.awt.Color(218, 189, 102));
        choice4.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        choice4.setForeground(new java.awt.Color(255, 255, 255));
        choice4.setText("choix 4");
        choice4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice4ActionPerformed(evt);
            }
        });
        jPanel6.add(choice4, new org.netbeans.lib.awtextra.AbsoluteConstraints(617, 370, 120, -1));

        choice3.setBackground(new java.awt.Color(103, 109, 213));
        choice3.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        choice3.setForeground(new java.awt.Color(255, 255, 255));
        choice3.setText("choix 3");
        choice3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice3ActionPerformed(evt);
            }
        });
        jPanel6.add(choice3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, 120, -1));

        startButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });
        jPanel6.add(startButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 330, 150, -1));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N

        user1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        user1.setText("User1");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(user1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(user1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 200, 90));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        user2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        user2.setText("User2");

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(user2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap(21, Short.MAX_VALUE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(19, 19, 19))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                                .addComponent(user2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(27, 27, 27))))
        );

        jPanel6.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 200, 90));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N

        user3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        user3.setText("User3");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(user3, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
                jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel12Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel12Layout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addComponent(user3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 80, 190, 90));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N

        user4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        user4.setText("User4");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(user4, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
                jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addContainerGap(21, Short.MAX_VALUE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(user4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, 190, 90));

        jPanel14.setBackground(new java.awt.Color(253, 253, 253));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("Time left:");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addContainerGap(758, Short.MAX_VALUE)
                                .addComponent(jLabel32)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
        );
        jPanel14Layout.setVerticalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel23)
                                        .addComponent(jLabel32))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 880, 70));

        jPanel8.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 880, 410));

        jTabbedPane1.addTab("tab4", jPanel8);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Number of questions: ");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 90, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(29, 159, 243));
        jLabel16.setText("Join a session");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(29, 159, 243));
        jLabel14.setText("Create a session");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, -1, -1));

        jPanel4.add(sessionName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 267, -1));

        createSessionButton.setBackground(new java.awt.Color(62, 115, 189));
        createSessionButton.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        createSessionButton.setForeground(new java.awt.Color(255, 255, 255));
        createSessionButton.setText("Create");
        createSessionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createSessionButtonActionPerformed(evt);
            }
        });
        jPanel4.add(createSessionButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 110, 50));

        signOutButton.setBackground(new java.awt.Color(255, 45, 45));
        signOutButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        signOutButton.setForeground(new java.awt.Color(255, 255, 255));
        signOutButton.setText("Sign Out");
        signOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signOutButtonActionPerformed(evt);
            }
        });
        jPanel4.add(signOutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, -1, -1));

        connectedUsername.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        connectedUsername.setForeground(new java.awt.Color(255, 255, 255));
        connectedUsername.setText("Username");
        jPanel4.add(connectedUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.jpg"))); // NOI18N
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 50));

        sessionTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Author", "Session Name", "Themes", "N?? Questions"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sessionTable.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(sessionTable);
        sessionTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (sessionTable.getColumnModel().getColumnCount() > 0) {
            sessionTable.getColumnModel().getColumn(0).setResizable(false);
            sessionTable.getColumnModel().getColumn(1).setResizable(false);
            sessionTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 870, 150));

        joinSessionButton.setBackground(new java.awt.Color(71, 116, 252));
        joinSessionButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        joinSessionButton.setForeground(new java.awt.Color(255, 255, 255));
        joinSessionButton.setText("Join");
        joinSessionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinSessionButtonActionPerformed(evt);
            }
        });
        jPanel4.add(joinSessionButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 420, 90, -1));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Name of session: ");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, -1, -1));
        jPanel4.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, 50, 30));

        jScrollPane2.setViewportView(textJoinSession);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 420, 180, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Name of session: ");
        jPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        themeTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Theme", "Selection"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                    false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(themeTable);
        if (themeTable.getColumnModel().getColumnCount() > 0) {
            themeTable.getColumnModel().getColumn(0).setResizable(false);
            themeTable.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 440, 100));

        jTabbedPane1.addTab("tab3", jPanel4);

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        score3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        score3.setForeground(new java.awt.Color(255, 255, 255));
        score3.setText("");
        jPanel9.add(score3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 420, -1));

        leaveButton.setBackground(new java.awt.Color(114, 157, 217));
        leaveButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        leaveButton.setForeground(new java.awt.Color(255, 255, 255));
        leaveButton.setText("Leave session");
        leaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveButtonActionPerformed(evt);
            }
        });
        jPanel9.add(leaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, -1, -1));

        score4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        score4.setForeground(new java.awt.Color(255, 255, 255));
        score4.setText("");
        jPanel9.add(score4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 420, -1));

        score1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        score1.setForeground(new java.awt.Color(255, 255, 255));
        score1.setText("");
        jPanel9.add(score1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 420, -1));

        score2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        score2.setForeground(new java.awt.Color(255, 255, 255));
        score2.setText("");
        jPanel9.add(score2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 420, -1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Scores");
        jPanel9.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 100, -1));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.jpg"))); // NOI18N
        jPanel9.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 420));

        jTabbedPane1.addTab("tab5", jPanel9);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, -39, 1030, 570));

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }


    private void createSessionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Integer howManyQuestions = (Integer) jSpinner1.getValue();

        if (sessionName.getText() == null || howManyQuestions <= 0 || howManyQuestions > 10 ) {
            return;
        }

        List<String> themes = new ArrayList<>();

        for (int i = 0; i < themeTable.getModel().getRowCount(); i++) {
            if ((boolean) themeTable.getModel().getValueAt(i, 1)) {
                themes.add((String) themeTable.getModel().getValueAt(i, 0));
            }
        }

        if (themes.size() == 0) {
            return;
        }

        try {
            if (client.add_party(sessionName.getText(), howManyQuestions, themes)) {
                jLabel17.setText(client.getMySession().getPartyName());
                startButton.setVisible(true);
                jTabbedPane1.setSelectedIndex(2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateMySessionGame(Party party) {
        // System.out.println(party.getLastWinnerQuestion());
        // System.out.println("winner question");

        if (!party.getAuthorKey().contentEquals((String) client.getMe().getKey()) || party.getCurrentQuestion() > 0) {
            startButton.setVisible(false);
        }

        user1.setText("");
        user2.setText("");
        user3.setText("");
        user4.setText("");

        int i = 0;
        for (String username:
             party.getParticipants().keySet()) {
            switch (i) {
                case 0 -> {
                    user1.setText(username + " - SCORE: " + party.getParticipants().get(username).get()) ;
                    if (party.getLastWinnerQuestion() != null && username.contentEquals(party.getLastWinnerQuestion())) {
                        jPanel10.setBackground(new java.awt.Color(0, 255, 0));
                    } else {
                        jPanel10.setBackground(new java.awt.Color(167, 183, 255));
                    }
                }
                case 1 -> {
                    user2.setText(username + " - SCORE: " + party.getParticipants().get(username).get());
                    if (party.getLastWinnerQuestion() != null && username.contentEquals(party.getLastWinnerQuestion())) {
                        jPanel11.setBackground(new java.awt.Color(0, 255, 0));
                    } else {
                        jPanel11.setBackground(new java.awt.Color(167, 183, 255));
                    }
                }
                case 2 -> {
                    user3.setText(username + " - SCORE: " + party.getParticipants().get(username).get());
                    if (party.getLastWinnerQuestion() != null && username.contentEquals(party.getLastWinnerQuestion())) {
                        jPanel12.setBackground(new java.awt.Color(0, 255, 0));
                    } else {
                        jPanel12.setBackground(new java.awt.Color(167, 183, 255));
                    }
                }
                case 3 -> {
                    user4.setText(username + " - SCORE: " + party.getParticipants().get(username).get());
                    if (party.getLastWinnerQuestion() != null && username.contentEquals(party.getLastWinnerQuestion())) {
                        jPanel13.setBackground(new java.awt.Color(0, 255, 0));
                    } else {
                        jPanel13.setBackground(new java.awt.Color(167, 183, 255));
                    }
                }
            }
            i++;
        }

        if (party.getLastWinnerQuestion() != null) {
            choice1.setVisible(false);
            choice2.setVisible(false);
            choice3.setVisible(false);
            choice4.setVisible(false);
            client.setGive_answer(false);
            return;
        } else {
            if (client.isGive_answer()) {
                if (client.getMySession().getParticipants().size() == 1) {
                    client.setGive_answer(false);
                }
                return;
            }
            jPanel14.setBackground(new java.awt.Color(253, 253, 253));
        }

        choice1.setText("");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");

        if (party.getCurrentQuestion() == 0) {
            choice1.setVisible(false);
            choice2.setVisible(false);
            choice3.setVisible(false);
            choice4.setVisible(false);
            return;
        }

        try {
            Predicate<Fichier> predicate = fichier -> ((int) fichier.getKey()) == ((int) party.getFichiersOrder().get(party.getCurrentQuestion() - 1).getKey());
            Optional<Fichier> currentFichierQuestion = party.getQuestions().keySet().stream().filter(predicate).findFirst();

            if (currentFichierQuestion.isEmpty()) {
                System.out.println("no files!");
                return;
            }

            i = 0;
            for (Reponse reponse:
                    party.getQuestions().get(currentFichierQuestion.get())) {
                switch (i) {
                    case 0 -> choice1.setText(reponse.getValue());
                    case 1 -> choice2.setText(reponse.getValue());
                    case 2 -> choice3.setText(reponse.getValue());
                    case 3 -> choice4.setText(reponse.getValue());
                }
                i++;
            }

            try {
                String path = "/images/"+ currentFichierQuestion.get().getName() + "." + currentFichierQuestion.get().getExtension();
                // System.out.println(path);
                ImageIcon icon = new ImageIcon(getClass().getResource(path));
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(jLabel19.getWidth(), jLabel19.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImg) ;
                jLabel19.setIcon(scaledIcon);
            } catch (Exception ignored) {
            }

            jLabel19.setVisible(true);
            choice1.setVisible(true);
            choice2.setVisible(true);
            choice3.setVisible(true);
            choice4.setVisible(true);
        } catch (IndexOutOfBoundsException e) {
            if (party.getCurrentQuestion() == 0) {
                return;
            }

            jTabbedPane1.setSelectedIndex(4);

            int j = 0;
            for (String participant:
                 party.getParticipants().keySet()) {
                switch (j) {
                    case 0 -> score1.setText(String.format("%s                                    %d", participant, party.getParticipants().get(participant).get()));
                    case 1 -> score2.setText(String.format("%s                                    %d", participant, party.getParticipants().get(participant).get()));
                    case 2 -> score3.setText(String.format("%s                                    %d", participant, party.getParticipants().get(participant).get()));
                    case 3 -> score4.setText(String.format("%s                                    %d", participant, party.getParticipants().get(participant).get()));
                }
                j++;
            }

            jLabel19.setVisible(false);
            startButton.setVisible(true);
        }
    }

    public void updateTimer(Timer timer) {
        jLabel23.setText(String.valueOf(timer.getSeconds().get()));
    }



    private void choice2ActionPerformed(java.awt.event.ActionEvent evt) {
        String response = choice2.getText();
        try {
            if (!client.send_party_choice(new Reponse(response))) {
                choice1.setVisible(false);
                choice2.setVisible(false);
                choice3.setVisible(false);
                choice4.setVisible(false);
                jPanel14.setBackground(new java.awt.Color(255, 70, 70));
            } else {
                jPanel14.setBackground(new java.awt.Color(94, 191, 96));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void choice1ActionPerformed(java.awt.event.ActionEvent evt) {
        String response = choice1.getText();
        try {
            if (!client.send_party_choice(new Reponse(response))) {
                choice1.setVisible(false);
                choice2.setVisible(false);
                choice3.setVisible(false);
                choice4.setVisible(false);
                jPanel14.setBackground(new java.awt.Color(255, 70, 70));
            } else {
                jPanel14.setBackground(new java.awt.Color(94, 191, 96));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void choice3ActionPerformed(java.awt.event.ActionEvent evt) {
        String response = choice3.getText();
        try {
            if (!client.send_party_choice(new Reponse(response))) {
                choice1.setVisible(false);
                choice2.setVisible(false);
                choice3.setVisible(false);
                choice4.setVisible(false);
                jPanel14.setBackground(new java.awt.Color(255, 70, 70));
            } else {
                jPanel14.setBackground(new java.awt.Color(94, 191, 96));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void choice4ActionPerformed(java.awt.event.ActionEvent evt) {
        String response = choice4.getText();
        try {
            if (!client.send_party_choice(new Reponse(response))) {
                choice1.setVisible(false);
                choice2.setVisible(false);
                choice3.setVisible(false);
                choice4.setVisible(false);
                jPanel14.setBackground(new java.awt.Color(255, 70, 70));
            } else {
                jPanel14.setBackground(new java.awt.Color(94, 191, 96));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void joinSessionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (textJoinSession.getText() == null || textJoinSession.getText().trim().isEmpty()) {
            return;
        }

        try {
            if (client.join_party(textJoinSession.getText())) {
                jLabel17.setText(client.getMySession().getPartyName());
                startButton.setVisible(false);
                jTabbedPane1.setSelectedIndex(2);
            }
        } catch (IOException e) {
            // problem
        }
    }

    private void signOutButtonActionPerformed(java.awt.event.ActionEvent evt)  {
        try {
            if (client.signOut()) {
                connectedUsername.setText("");
                jTabbedPane1.setSelectedIndex(0);
            }
        } catch (IOException e) {
            // problem
        }
    }

    private void signUpButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (signUpUsername.getText() != null && signUpPassword.getPassword() != null) {
            if (Arrays.equals(signUpPassword.getPassword(), signUpConfirmPassword.getPassword())) {
                try {
                    if (client.signUp(signUpUsername.getText(), String.valueOf(signUpPassword.getPassword()))) {
                        jTabbedPane1.setSelectedIndex(0);
                    } else {
                        // wrong credentials
                    }
                } catch (IOException e) {
                    // user already exist
                }
            } else {
                // pass do not match
            }
        }
    }

    private void signInButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if (signInUsername.getText() != null && signInPassword.getPassword() != null) {
            try {
                if (client.signIn(signInUsername.getText(), String.valueOf(signInPassword.getPassword()))) {
                    System.out.println(client.getMe());
                    connectedUsername.setText(client.getMe().getUsername());
                    client.get_themes();

                    jTabbedPane1.setSelectedIndex(3);
                }
            } catch (IOException e) {
                // wrong credential
            }
        }

        signInUsername.setText("");
        signInPassword.setText("");
    }

    private void redirectToSignUpButtonActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane1.setSelectedIndex(1);
    }

    private void redirectToSignInButtonActionPerformed(java.awt.event.ActionEvent evt) {
        jTabbedPane1.setSelectedIndex(0);
    }

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (client.start_party(client.getMySession().getPartyName())) {
                startButton.setVisible(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void leaveButtonActionPerformed(java.awt.event.ActionEvent evt)  {
        try {
            client.leaveSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clearComponents();
        jTabbedPane1.setSelectedIndex(3);
    }


    private void clearComponents() {
        sessionName.setText("");
        jSpinner1.setValue(0);
        textJoinSession.setText("");
        jLabel23.setText("") ;
        score1.setText("");
        score2.setText("");
        score3.setText("");
        score4.setText("");

        for (int i = 0; i < themeTable.getModel().getRowCount(); i++) {
                themeTable.getModel().setValueAt(false,i,1);
        }

    }

    // Variables declaration
    private javax.swing.JButton choice1;
    private javax.swing.JButton choice2;
    private javax.swing.JButton choice3;
    private javax.swing.JButton choice4;
    private javax.swing.JButton createSessionButton;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel connectedUsername;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane textJoinSession;
    private javax.swing.JButton joinSessionButton;
    private javax.swing.JButton leaveButton;
    private javax.swing.JButton redirectToSignInButton;
    private javax.swing.JButton redirectToSignUpButton;
    private javax.swing.JLabel score1;
    private javax.swing.JLabel score2;
    private javax.swing.JLabel score3;
    private javax.swing.JLabel score4;
    private javax.swing.JTextField sessionName;
    private javax.swing.JTable sessionTable;
    private javax.swing.JButton signInButton;
    private javax.swing.JPasswordField signInPassword;
    private javax.swing.JTextField signInUsername;
    private javax.swing.JButton signOutButton;
    private javax.swing.JButton signUpButton;
    private javax.swing.JPasswordField signUpConfirmPassword;
    private javax.swing.JPasswordField signUpPassword;
    private javax.swing.JTextField signUpUsername;
    private javax.swing.JButton startButton;
    private javax.swing.JTable themeTable;
    private javax.swing.JLabel user1;
    private javax.swing.JLabel user2;
    private javax.swing.JLabel user3;
    private javax.swing.JLabel user4;
    // End of variables declaration
}
