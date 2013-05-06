package com.seitenbau.testing.dbunit.dao;

import java.sql.Types;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class ProfessorUpdateQuery extends SqlUpdate
{
  private static final String PROFESSOR_UPDATE_QUERY = "UPDATE " + //
      Repo.DB_PROFESSORS_TABLE_NAME + //
      " SET " + //
      ProfessorColumnNames.NAME.getColumnName() + " = ?, " + //
      ProfessorColumnNames.FIRST_NAME.getColumnName() + " = ?, " + //
      ProfessorColumnNames.TITLE.getColumnName() + " = ?, " + //
      ProfessorColumnNames.FACULTY.getColumnName() + " = ?" + //
      " WHERE " + //
      ProfessorColumnNames.ID.getColumnName() + " = ?";

  public ProfessorUpdateQuery(JdbcTemplate template)
  {
    super(template.getDataSource(), PROFESSOR_UPDATE_QUERY);

    declareParameter(new SqlParameter(ProfessorColumnNames.NAME.getColumnName(), Types.VARCHAR));
    declareParameter(new SqlParameter(ProfessorColumnNames.FIRST_NAME.getColumnName(), Types.VARCHAR));
    declareParameter(new SqlParameter(ProfessorColumnNames.TITLE.getColumnName(), Types.VARCHAR));
    declareParameter(new SqlParameter(ProfessorColumnNames.FACULTY.getColumnName(), Types.VARCHAR));
    declareParameter(new SqlParameter(ProfessorColumnNames.ID.getColumnName(), Types.INTEGER));
    compile();
  }

  public int update(Professor toUpdate) throws DataAccessException
  {
    int affectedRows = super.update( //
        toUpdate.getName(), //
        toUpdate.getFirstName(), //
        toUpdate.getTitle(), //
        toUpdate.getFaculty(), //
        toUpdate.getId() //
        );

    return affectedRows;
  }
}