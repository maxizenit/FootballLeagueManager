package ru.maxizenit.footballleaguemanager.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;
import ru.maxizenit.footballleaguemanager.dto.TournamentResultDto;
import ru.maxizenit.footballleaguemanager.entity.Goal;
import ru.maxizenit.footballleaguemanager.entity.Match;
import ru.maxizenit.footballleaguemanager.entity.Player;
import ru.maxizenit.footballleaguemanager.entity.Position;
import ru.maxizenit.footballleaguemanager.entity.Squad;
import ru.maxizenit.footballleaguemanager.entity.Team;
import ru.maxizenit.footballleaguemanager.exception.InvalidFieldException;
import ru.maxizenit.footballleaguemanager.gui.dialog.entitydialog.MatchDialog;
import ru.maxizenit.footballleaguemanager.gui.dialog.entitydialog.PlayerDialog;
import ru.maxizenit.footballleaguemanager.gui.dialog.entitydialog.PositionDialog;
import ru.maxizenit.footballleaguemanager.gui.dialog.entitydialog.TeamDialog;
import ru.maxizenit.footballleaguemanager.gui.entitytable.EntityTable;
import ru.maxizenit.footballleaguemanager.gui.entitytable.filter.PlayerScrollPaneTableFilter;
import ru.maxizenit.footballleaguemanager.gui.entitytable.model.*;
import ru.maxizenit.footballleaguemanager.gui.matchcalendar.MatchCalendar;
import ru.maxizenit.footballleaguemanager.service.GoalService;
import ru.maxizenit.footballleaguemanager.service.MatchService;
import ru.maxizenit.footballleaguemanager.service.PlayerService;
import ru.maxizenit.footballleaguemanager.service.PositionService;
import ru.maxizenit.footballleaguemanager.service.SquadService;
import ru.maxizenit.footballleaguemanager.service.TeamService;
import ru.maxizenit.footballleaguemanager.util.calculator.TournamentResultCalculator;
import ru.maxizenit.footballleaguemanager.util.report.PlayerReportGenerator;
import ru.maxizenit.footballleaguemanager.util.xmlhandler.TeamXMLHandler;

@Component
public class MainForm extends JFrame {

  private final GoalService goalService;

  private final MatchService matchService;

  private final PlayerService playerService;

  private final PositionService positionService;

  private final SquadService squadService;

  private final TeamService teamService;

  private JPanel mainPanel;

  //Вкладка "Информация о команде"
  private JComboBox<Team> teamCombo;

  private EntityTable<Player> teamPlayerTable;

  private JButton exportPlayersToHTMLButton;

  private EntityTable<TournamentResultDto> tournamentTable;

  //Вкладка "Матчи"
  private MatchCalendar matchCalendar;

  private EntityTable<Match> matchTable;

  private JButton addMatchButton;

  private JButton editMatchButton;

  private JButton removeMatchButton;

  private EntityTable<Squad> homeSquadTable;

  private EntityTable<Squad> guestSquadTable;

  private EntityTable<Goal> homeGoalTable;

  private EntityTable<Goal> guestGoalTable;

  //Вкладка "Игроки"
  private EntityTable<Player> playerTable;

  private JButton addPlayerButton;

  private JButton editPlayerButton;

  private JButton removePlayerButton;

  private JButton removePositionButton;

  private JComboBox<Team> teamPlayerFilterField;

  private JComboBox<Position> positionPlayerFilterField;

  private JTextField lastNamePlayerFilterField;

  private JTextField firstNamePlayerFilterField;

  private JTextField shortNamePlayerFilterField;

  private JTextField minAgePlayerFilterField;

  private JTextField maxAgePlayerFilterField;

  private JButton playerFilterButton;

  private JButton clearPlayerFilterButton;

  //Вкладка "Команды"
  private EntityTable<Team> teamTable;

  private JButton addTeamButton;

  private JButton editTeamButton;

  private JButton removeTeamButton;

  private JButton addTeamsFromXMLButton;

  private JButton saveTeamsToXMLButton;

  private JButton replaceTeamsFromXMLButton;

  //Вкладка "Позиции"
  private EntityTable<Position> positionTable;

  private JButton addPositionButton;

  private JButton editPositionButton;

  @Autowired
  public MainForm(GoalService goalService, MatchService matchService, PlayerService playerService,
      PositionService positionService, SquadService squadService, TeamService teamService) {
    this.goalService = goalService;
    this.matchService = matchService;
    this.playerService = playerService;
    this.positionService = positionService;
    this.squadService = squadService;
    this.teamService = teamService;

    $$$setupUI$$$();
    initGUI();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setContentPane(mainPanel);
    pack();
    setVisible(true);
  }

  private void showMessageDialog(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  private void createUIComponents() {
    TeamPlayerTableModel teamPlayerTableModel = new TeamPlayerTableModel(playerService);
    teamPlayerTable = new EntityTable<>(teamPlayerTableModel);
    tournamentTable = new EntityTable<>(new TournamentResultTableModel());

    matchTable = new EntityTable<>(new MatchTableModel(goalService));
    matchTable.addMouseListener(new MouseClickedListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Match match = matchTable.getSelectedEntity();

        if (match != null) {
          refreshSquadTables(match);
          refreshGoalTables(match);
        }
      }
    });

    homeSquadTable = new EntityTable<>(new SquadTableModel());
    guestSquadTable = new EntityTable<>(new SquadTableModel());
    homeGoalTable = new EntityTable<>(new GoalTableModel());
    guestGoalTable = new EntityTable<>(new GoalTableModel());

    playerTable = new EntityTable<>(new PlayerTableModel());

    teamTable = new EntityTable<>(new TeamTableModel());
    positionTable = new EntityTable<>(new PositionTableModel());
  }

  private void initGUI() {
    initTeamCombo();
    addTeamInfoButtonsListeners();
    refreshTournamentTable();
    refreshMatchTable();
    addMatchButtonsListeners();
    refreshPlayerTable();
    addPlayerButtonsListeners();
    initPlayerFilterFields();
    addPlayerFilterButtonsListeners();
    refreshTeamTable();
    addTeamButtonsListeners();
    refreshPositionTable();
    addPositionButtonsListeners();
  }

  private void initTeamCombo() {
    DefaultComboBoxModel<Team> model = new DefaultComboBoxModel<>();
    teamCombo.setModel(model);
    refreshTeamCombo();

    teamCombo.addActionListener(a -> {
      Team team = (Team) teamCombo.getSelectedItem();

      if (team != null) {
        refreshTeamPlayerTable(team);
        matchCalendar.setMatches(matchService.getMatchesByTeam(team), team, goalService);
        exportPlayersToHTMLButton.setEnabled(true);
      }
    });
  }

  private void refreshTeamCombo() {
    DefaultComboBoxModel<Team> model = (DefaultComboBoxModel<Team>) teamCombo.getModel();
    model.removeAllElements();
    model.addAll(teamService.getAll());
  }

  private void refreshTeamPlayerTable(Team team) {
    teamPlayerTable.updateEntities(playerService.getPlayersByTeam(team));
  }

  private void addTeamInfoButtonsListeners() {
    PlayerReportGenerator reportGenerator = new PlayerReportGenerator();
    JFileChooser chooser = new JFileChooser();

    exportPlayersToHTMLButton.addActionListener(a -> {
      Team team = (Team) teamCombo.getSelectedItem();

      if (team != null) {
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
          try {
            reportGenerator.generate(chooser.getSelectedFile(),
                playerService.getPlayersByTeam(team));
          } catch (JRException | IOException e) {
            showMessageDialog(e.getMessage());
          }
        }
      }
    });
  }

  private void refreshTournamentTable() {
    tournamentTable.updateEntities(
        TournamentResultCalculator.calculate(teamService.getAll(), matchService::getMatchesByTeam,
            goalService));
  }

  private void refreshMatchTable() {
    matchTable.updateEntities(matchService.getAll());
  }

  private void addMatchButtonsListeners() {
    addMatchButton.addActionListener(
        a -> new MatchDialog(this, matchService::save, this::refreshMatchTable, null,
            teamService.getAll()));

    editMatchButton.addActionListener(a -> {
      Match match = matchTable.getSelectedEntity();

      if (match != null) {
        new MatchDialog(this, matchService::save, this::refreshMatchTable, match,
            teamService.getAll());
      }
    });

    removeMatchButton.addActionListener(a -> {
      Match match = matchTable.getSelectedEntity();

      if (match != null) {
        matchService.remove(match);
        refreshMatchTable();
      }
    });
  }

  private void refreshSquadTables(Match match) {
    homeSquadTable.updateEntities(squadService.getSquadsByMatchAndTeam(match, match.getHomeTeam()));
    guestSquadTable.updateEntities(
        squadService.getSquadsByMatchAndTeam(match, match.getGuestTeam()));
  }

  private void refreshGoalTables(Match match) {
    homeGoalTable.updateEntities(goalService.getGoalsByMatchAndTeam(match, match.getHomeTeam()));
    guestGoalTable.updateEntities(goalService.getGoalsByMatchAndTeam(match, match.getGuestTeam()));
  }

  private void refreshPlayerTable() {
    playerTable.updateEntities(playerService.getAll());
  }

  private void addPlayerButtonsListeners() {
    addPlayerButton.addActionListener(
        a -> new PlayerDialog(this, playerService::save, this::refreshPlayerTable, null,
            teamService.getAll(), positionService.getAll()));

    editPlayerButton.addActionListener(a -> {
      Player player = playerTable.getSelectedEntity();

      if (player != null) {
        new PlayerDialog(this, playerService::save, this::refreshPlayerTable, player,
            teamService.getAll(), positionService.getAll());
      }
    });

    removePlayerButton.addActionListener(a -> {
      Player player = playerTable.getSelectedEntity();

      if (player != null) {
        playerService.remove(player);
        refreshPlayerTable();
      }
    });
  }

  private void initPlayerFilterFields() {
    DefaultComboBoxModel<Team> teamModel = new DefaultComboBoxModel<>();
    teamModel.addAll(teamService.getAll());
    teamPlayerFilterField.setModel(teamModel);

    DefaultComboBoxModel<Position> positionModel = new DefaultComboBoxModel<>();
    positionModel.addAll(positionService.getAll());
    positionPlayerFilterField.setModel(positionModel);
  }

  private void addPlayerFilterButtonsListeners() {
    playerFilterButton.addActionListener(a -> {
      try {
        PlayerScrollPaneTableFilter filter = new PlayerScrollPaneTableFilter();

        filter.setTeam((Team) teamPlayerFilterField.getSelectedItem());
        filter.setPosition((Position) positionPlayerFilterField.getSelectedItem());
        filter.setLastName(lastNamePlayerFilterField.getText());
        filter.setFirstName(firstNamePlayerFilterField.getText());
        filter.setShortName(shortNamePlayerFilterField.getText());
        filter.setMinAge(StringUtils.hasText(minAgePlayerFilterField.getText()) ? Integer.parseInt(
            minAgePlayerFilterField.getText()) : null);
        filter.setMaxAge(StringUtils.hasText(maxAgePlayerFilterField.getText()) ? Integer.parseInt(
            maxAgePlayerFilterField.getText()) : null);

        playerTable.filter(filter);
      } catch (InvalidFieldException | NumberFormatException e) {
        showMessageDialog(e.getMessage());
      }
    });

    clearPlayerFilterButton.addActionListener(a -> {
      teamPlayerFilterField.setSelectedItem(null);
      positionPlayerFilterField.setSelectedItem(null);
      lastNamePlayerFilterField.setText(null);
      firstNamePlayerFilterField.setText(null);
      shortNamePlayerFilterField.setText(null);
      minAgePlayerFilterField.setText(null);
      maxAgePlayerFilterField.setText(null);
    });
  }

  private void refreshTeamTable() {
    teamTable.updateEntities(teamService.getAll());
  }

  private void addTeamButtonsListeners() {
    addTeamButton.addActionListener(a -> {
      new TeamDialog(this, teamService::save, this::refreshTeamTable, null);
      refreshTeamCombo();
      initPlayerFilterFields();
    });

    editTeamButton.addActionListener(a -> {
      Team team = teamTable.getSelectedEntity();

      if (team != null) {
        new TeamDialog(this, teamService::save, this::refreshTeamTable, team);
        refreshTeamCombo();
        initPlayerFilterFields();
      }
    });

    removeTeamButton.addActionListener(a -> {
      Team team = teamTable.getSelectedEntity();

      if (team != null) {
        teamService.remove(team);
        refreshTeamTable();
        refreshTeamCombo();
        initPlayerFilterFields();
      }
    });

    JFileChooser chooser = new JFileChooser();
    TeamXMLHandler handler = new TeamXMLHandler();

    addTeamsFromXMLButton.addActionListener(a -> {
      int result = chooser.showOpenDialog(this);

      if (result == JFileChooser.APPROVE_OPTION) {
        try {
          teamService.saveAll(handler.loadFromXML(chooser.getSelectedFile()));
          refreshTeamTable();
          refreshTeamCombo();
          initPlayerFilterFields();
        } catch (IOException | SAXException e) {
          showMessageDialog(e.getMessage());
        }
      }
    });

    saveTeamsToXMLButton.addActionListener(a -> {
      int result = chooser.showSaveDialog(this);

      if (result == JFileChooser.APPROVE_OPTION) {
        handler.saveToXML(chooser.getSelectedFile(), teamService.getAll());
      }
    });

    replaceTeamsFromXMLButton.addActionListener(a -> {
      int result = chooser.showOpenDialog(this);

      if (result == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        List<Team> teams = new ArrayList<>();

        Thread removeThread = new Thread(teamService::removeAll);
        Thread loadThread = new Thread(() -> {
          try {
            teamService.saveAll(handler.loadFromXML(file));
          } catch (IOException | SAXException e) {
            SwingUtilities.invokeLater(() -> showMessageDialog(e.getMessage()));
          }
        });
        Thread saveThread = new Thread(() -> teamService.saveAll(teams));

        try {
          removeThread.start();
          loadThread.start();

          removeThread.join();
          loadThread.join();

          saveThread.start();
          saveThread.join();
        } catch (InterruptedException e) {
          removeThread.interrupt();
          loadThread.interrupt();
          saveThread.interrupt();

          showMessageDialog(e.getMessage());
        } finally {
          refreshTeamTable();
          refreshTeamCombo();
          initPlayerFilterFields();
        }
      }
    });
  }

  private void refreshPositionTable() {
    positionTable.updateEntities(positionService.getAll());
  }

  private void addPositionButtonsListeners() {
    addPositionButton.addActionListener(a -> {
      new PositionDialog(this, positionService::save, this::refreshPositionTable, null);
      initPlayerFilterFields();
    });

    editPositionButton.addActionListener(a -> {
      Position position = positionTable.getSelectedEntity();

      if (position != null) {
        new PositionDialog(this, positionService::save, this::refreshPositionTable, position);
        initPlayerFilterFields();
      }
    });

    removePositionButton.addActionListener(a -> {
      Position position = positionTable.getSelectedEntity();

      if (position != null) {
        positionService.remove(position);
        refreshPositionTable();
        initPlayerFilterFields();
      }
    });
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    createUIComponents();
    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    mainPanel.setPreferredSize(new Dimension(800, 600));
    final JTabbedPane tabbedPane1 = new JTabbedPane();
    mainPanel.add(tabbedPane1,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
            new Dimension(200, 200), null, 0, false));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
    tabbedPane1.addTab("Информация о команде", panel1);
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel2,
        new GridConstraints(1, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Игроки",
        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    panel2.add(teamPlayerTable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    exportPlayersToHTMLButton = new JButton();
    exportPlayersToHTMLButton.setText("Экспорт в HTML");
    panel2.add(exportPlayersToHTMLButton,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel3,
        new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
        "Матчи и турнирная таблица", TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION, null, null));
    matchCalendar = new MatchCalendar();
    panel3.add(matchCalendar,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel4, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label1 = new JLabel();
    label1.setText("Выбранная команда");
    panel4.add(label1,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    teamCombo = new JComboBox();
    panel4.add(teamCombo, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final Spacer spacer1 = new Spacer();
    panel1.add(spacer1, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    panel1.add(tournamentTable,
        new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
    tabbedPane1.addTab("Матчи", panel5);
    final JPanel panel6 = new JPanel();
    panel6.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
    panel5.add(panel6,
        new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Матчи",
        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    panel6.add(matchTable,
        new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    addMatchButton = new JButton();
    addMatchButton.setText("Добавить");
    panel6.add(addMatchButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    editMatchButton = new JButton();
    editMatchButton.setText("Изменить");
    panel6.add(editMatchButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    removeMatchButton = new JButton();
    removeMatchButton.setText("Удалить");
    panel6.add(removeMatchButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel7 = new JPanel();
    panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel5.add(panel7,
        new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    panel7.setBorder(
        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Состав хозяев",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    panel7.add(homeSquadTable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JPanel panel8 = new JPanel();
    panel8.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel5.add(panel8,
        new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    panel8.setBorder(
        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Состав гостей",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    panel8.add(guestSquadTable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JPanel panel9 = new JPanel();
    panel9.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel5.add(panel9,
        new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    panel9.setBorder(
        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Голы хозяев",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    panel9.add(homeGoalTable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JPanel panel10 = new JPanel();
    panel10.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel5.add(panel10,
        new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    panel10.setBorder(
        BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Голы гостей",
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    panel10.add(guestGoalTable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JPanel panel11 = new JPanel();
    panel11.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
    tabbedPane1.addTab("Игроки", panel11);
    final JPanel panel12 = new JPanel();
    panel12.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
    panel11.add(panel12,
        new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
            new Dimension(9, 83), null, 0, false));
    panel12.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Игроки",
        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    panel12.add(playerTable,
        new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    addPlayerButton = new JButton();
    addPlayerButton.setText("Добавить");
    panel12.add(addPlayerButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    editPlayerButton = new JButton();
    editPlayerButton.setText("Изменить");
    panel12.add(editPlayerButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    removePlayerButton = new JButton();
    removePlayerButton.setText("Удалить");
    panel12.add(removePlayerButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel13 = new JPanel();
    panel13.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel11.add(panel13, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    panel13.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Фильтр",
        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    final JLabel label2 = new JLabel();
    label2.setText("Команда");
    panel13.add(label2,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label3 = new JLabel();
    label3.setText("Позиция");
    panel13.add(label3,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label4 = new JLabel();
    label4.setText("Фамилия");
    panel13.add(label4,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label5 = new JLabel();
    label5.setText("Имя");
    panel13.add(label5,
        new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label6 = new JLabel();
    label6.setText("Краткое имя");
    panel13.add(label6,
        new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label7 = new JLabel();
    label7.setText("Возраст");
    panel13.add(label7,
        new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JPanel panel14 = new JPanel();
    panel14.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel13.add(panel14,
        new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    playerFilterButton = new JButton();
    playerFilterButton.setText("Поиск");
    panel14.add(playerFilterButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    clearPlayerFilterButton = new JButton();
    clearPlayerFilterButton.setText("Очистить");
    panel14.add(clearPlayerFilterButton,
        new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    teamPlayerFilterField = new JComboBox();
    panel13.add(teamPlayerFilterField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    positionPlayerFilterField = new JComboBox();
    panel13.add(positionPlayerFilterField,
        new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    lastNamePlayerFilterField = new JTextField();
    panel13.add(lastNamePlayerFilterField,
        new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    firstNamePlayerFilterField = new JTextField();
    panel13.add(firstNamePlayerFilterField,
        new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    shortNamePlayerFilterField = new JTextField();
    panel13.add(shortNamePlayerFilterField,
        new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JPanel panel15 = new JPanel();
    panel15.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
    panel13.add(panel15,
        new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JLabel label8 = new JLabel();
    label8.setText("от");
    panel15.add(label8,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label9 = new JLabel();
    label9.setText("до");
    panel15.add(label9,
        new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    minAgePlayerFilterField = new JTextField();
    panel15.add(minAgePlayerFilterField,
        new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    maxAgePlayerFilterField = new JTextField();
    panel15.add(maxAgePlayerFilterField,
        new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final Spacer spacer2 = new Spacer();
    panel11.add(spacer2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final JPanel panel16 = new JPanel();
    panel16.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
    tabbedPane1.addTab("Команды", panel16);
    panel16.add(teamTable,
        new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    addTeamButton = new JButton();
    addTeamButton.setText("Добавить");
    panel16.add(addTeamButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    editTeamButton = new JButton();
    editTeamButton.setText("Изменить");
    panel16.add(editTeamButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    removeTeamButton = new JButton();
    removeTeamButton.setText("Удалить");
    panel16.add(removeTeamButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    addTeamsFromXMLButton = new JButton();
    addTeamsFromXMLButton.setText("Добавить из XML");
    panel16.add(addTeamsFromXMLButton,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    saveTeamsToXMLButton = new JButton();
    saveTeamsToXMLButton.setText("Сохранить в XML");
    panel16.add(saveTeamsToXMLButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    replaceTeamsFromXMLButton = new JButton();
    replaceTeamsFromXMLButton.setText("Заменить из XML");
    panel16.add(replaceTeamsFromXMLButton,
        new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel17 = new JPanel();
    panel17.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
    tabbedPane1.addTab("Позиции", panel17);
    panel17.add(positionTable,
        new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    addPositionButton = new JButton();
    addPositionButton.setText("Добавить");
    panel17.add(addPositionButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    editPositionButton = new JButton();
    editPositionButton.setText("Изменить");
    panel17.add(editPositionButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    removePositionButton = new JButton();
    removePositionButton.setText("Удалить");
    panel17.add(removePositionButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return mainPanel;
  }

}
