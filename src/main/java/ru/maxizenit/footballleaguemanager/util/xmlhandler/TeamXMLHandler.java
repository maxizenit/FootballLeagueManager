package ru.maxizenit.footballleaguemanager.util.xmlhandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import ru.maxizenit.footballleaguemanager.entity.Team;

/**
 * Обработчик XML для команд.
 */
public class TeamXMLHandler extends XMLHandler<Team> {

  private static final String ENTITY_NAME = "team";

  private static final String CODE_ATTR = "code";

  private static final String NAME_ATTR = "name";

  public TeamXMLHandler() {
    super(ENTITY_NAME);
  }

  @Override
  protected Team createEntityFromAttrs(NamedNodeMap attrs) {
    Team team = new Team();

    team.setCode(attrs.getNamedItem(CODE_ATTR).getNodeValue());
    team.setName(attrs.getNamedItem(NAME_ATTR).getNodeValue());

    return team;
  }

  @Override
  protected void setAttrsFromEntity(Element element, Team entity) {
    element.setAttribute(CODE_ATTR, entity.getCode());
    element.setAttribute(NAME_ATTR, entity.getName());
  }
}
