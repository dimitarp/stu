package com.seitenbau.testing.dbunit.datasets;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.datatype.DataType;


public class JavaDataSet extends DefaultDataSet
{

  public JavaDataSet() throws DataSetException 
  {
    DefaultTable persons = new DefaultTable("persons", new Column[] { //
        new Column("id", DataType.BIGINT), //
            new Column("first_name", DataType.VARCHAR), //
            new Column("name", DataType.VARCHAR), //
            new Column("team_id", DataType.BIGINT), //
        });
    persons.addRow(new Object[] { //
        Parameters.Persons.DENNIS, //
            "Dennis", //
            "Kaulbersch", //
            Parameters.Teams.QUALITY_ASSURANCE, //
        });
    persons.addRow(new Object[] { //
        Parameters.Persons.JULIEN, //
            "Julien", //
            "Guitton", //
            Parameters.Teams.QUALITY_ASSURANCE, //
        });
    persons.addRow(new Object[] { //
        Parameters.Persons.CHRISTIAN, //
            "Christian", //
            "Baranowski", //
            Parameters.Teams.QUALITY_ASSURANCE, //
        });

    DefaultTable jobs = new DefaultTable("jobs", new Column[] { //
        new Column("id", DataType.BIGINT), //
            new Column("title", DataType.VARCHAR), //
            new Column("description", DataType.VARCHAR), //
        });

    jobs.addRow(new Object[] { //
    Parameters.Jobs.SOFTWARE_DEVELOPER, //
        "Software Developer", //
        "Creating software", //
    });

    jobs.addRow(new Object[] { //
    Parameters.Jobs.SOFTWARE_TESTER, //
        "Software Tester", //
        "Testing software", //
    });

    jobs.addRow(new Object[] { //
    Parameters.Jobs.TEAM_MANAGER, //
        "Team Manager", //
        "Nobody knows", //
    });

    DefaultTable teams = new DefaultTable("teams", new Column[] { //
        new Column("id", DataType.BIGINT), //
            new Column("title", DataType.VARCHAR), //
            new Column("description", DataType.VARCHAR), //
            new Column("membersize", DataType.BIGINT), //
        });

    teams.addRow(new Object[] { //
        Parameters.Teams.QUALITY_ASSURANCE, //
            "Quality Assurance", //
            "Verifies that requirments for a product is fulfilled", //
            3, //
        });
    
    DefaultTable personJobs = new DefaultTable("person_jobs", new Column[] { //
        new Column("person_id", DataType.BIGINT), //
        new Column("job_id", DataType.BIGINT), //
        });

    personJobs.addRow(new Object[] { //
        Parameters.Persons.DENNIS, //
        Parameters.Jobs.SOFTWARE_DEVELOPER, //
    });
    
    personJobs.addRow(new Object[] { //
        Parameters.Persons.JULIEN, //
        Parameters.Jobs.SOFTWARE_TESTER, //
    });
    
    personJobs.addRow(new Object[] { //
        Parameters.Persons.CHRISTIAN, //
        Parameters.Jobs.TEAM_MANAGER, //
    });

    addTable(jobs);
    addTable(teams);
    addTable(persons);
    addTable(personJobs);
  }

}
