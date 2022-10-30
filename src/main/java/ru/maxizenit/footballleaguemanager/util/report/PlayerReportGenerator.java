package ru.maxizenit.footballleaguemanager.util.report;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import ru.maxizenit.footballleaguemanager.entity.Player;
import ru.maxizenit.footballleaguemanager.util.formatter.DateFormatter;
import ru.maxizenit.footballleaguemanager.util.report.databean.PlayerDataBean;

/**
 * Генератор HTML-отчёта JasperReports для игроков.
 */
public class PlayerReportGenerator extends ReportGenerator<Player, PlayerDataBean> {

  /**
   * Файл с шаблоном отчёта.
   */
  private static final Resource REPORT_PATTERN = new ClassPathResource("playerreport.jrxml");

  public PlayerReportGenerator() {
    super(REPORT_PATTERN);
  }

  @Override
  protected PlayerDataBean createDataBeanFromEntity(Player entity) {
    PlayerDataBean bean = new PlayerDataBean();

    bean.setPosition(entity.getPosition().getName());
    bean.setLastName(entity.getLastName());
    bean.setFirstName(entity.getFirstName());
    bean.setShortName(StringUtils.hasText(entity.getShortName()) ? entity.getShortName() : "");
    bean.setBirthdate(DateFormatter.format(entity.getBirthdate()));

    return bean;
  }
}
