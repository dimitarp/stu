package model;

import com.seitenbau.testing.dbunit.generator.DataType;
import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Table;

public class HochschuleModel extends DatabaseModel
{
  public HochschuleModel()
  {
    database("Hochschule");
    packageName("com.seitenbau.testing.hochschule.model");
    enableTableModelClassesGeneration();

    Table professoren = table("professor")
        .description("Die Tabelle mit den Professoren der Hochschule")
        .column("id", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("name", DataType.VARCHAR)
        .column("vorname", DataType.VARCHAR)
        .column("titel", DataType.VARCHAR)
        .column("fakultaet", DataType.VARCHAR)
      .build();

    Table lehrveranstaltungen = table("lehrveranstaltung")
        .description("Die Tabelle mit den Lehrveranstaltungen der Hochschule")
        .column("id", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("professor_id", DataType.BIGINT)
          .reference
            .local
              .name("geleitetVon")
              .description("Gibt an, von welchem Professor eine Lehrveranstaltung geleitet wird.")
            .foreign(professoren)
              .name("leitet")
              .description("Gibt an, welche Lehrveranstaltungen ein Professor leitet.")
              .multiplicity("0..*")
        .column("name", DataType.VARCHAR)
        .column("sws", DataType.INTEGER)
        .column("ects", DataType.DOUBLE)
      .build();

    Table pruefungen = table("pruefung")
        .description("Die Tabelle mit den Pr�fungen der Hochschule")
        .column("id", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("lehrveranstaltung_id", DataType.BIGINT)
          .reference
            .local
              .name("stoffVon")
              .multiplicity("1")
              .description("Gibt an, zu welcher Lehrvanstaltung eine Pr�fung geh�rt.")
            .foreign(lehrveranstaltungen)
              .name("hatPruefung")
              .multiplicity("0..*")
              .description("Ordnet Pr�fungen einer Lehrveranstaltung zu.")
        .column("typ", DataType.VARCHAR)
        .column("zeitpunkt", DataType.DATE)
      .build();

    Table studenten = table("student")
        .description("Die Tabelle mit den immatrikulierten Studenten der Hochschule")
        .column("matrikelnummer", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("name", DataType.VARCHAR)
        .column("vorname", DataType.VARCHAR)
        .column("studiengang", DataType.VARCHAR)
        .column("semester", DataType.INTEGER)
        .column("immatrikuliert_seit", DataType.DATE)
      .build();

    associativeTable("beaufsichtigt")
        .column("professor_id", DataType.BIGINT)
          .reference
            .foreign(professoren)
              .name("beaufsichtigt")
              .description("Gibt an, welche Pr�fungen ein Professor beaufsichtigt.")
              .multiplicity("0..*")
        .column("pruefung_id", DataType.BIGINT)
          .reference
            .foreign(pruefungen)
              .name("beaufsichtigtVon")
              .description("Gibt an, welche Professoren eine Pr�fung beaufsichtigen.")
              .multiplicity("0..*")
      .build();

    associativeTable("besucht")
        .column("student_id", DataType.BIGINT)
          .reference
            .foreign(studenten)
              .name("besucht")
              .description("Gibt an, welche Lehrveranstaltungen ein Student besucht.")
              .multiplicity("1")
        .column("lehrveranstaltung_id", DataType.BIGINT)
          .reference
            .foreign(lehrveranstaltungen)
              .name("besuchtVon")
              .description("Gibt an, welche Studenten eine Lehrveranstaltung besuchen.")
              .multiplicity("3..100")
      .build();

    associativeTable("isttutor")
        .column("student_id", DataType.BIGINT)
          .reference
            .foreign(studenten)
              .name("istTutor")
              .description("Gibt an, bei welchen Lehrveranstaltungen ein Student Tutor ist.")
        .column("lehrveranstaltung_id", DataType.BIGINT)
          .reference
            .foreign(lehrveranstaltungen)
              .name("hatTutor")
              .description("Gibt an, welche Tutoren eine Lehrveranstaltung hat.")
      .build();

    associativeTable("schreibt")
        .column("student_id", DataType.BIGINT)
          .reference
            .foreign(studenten)
              .name("schreibt")
              .description("Gibt an, welche Pr�fungen ein Student schreibt.")
        .column("pruefung_id", DataType.BIGINT)
          .reference
            .foreign(pruefungen)
              .name("geschriebenVon")
              .description("Gibt an, welche Studenten eine Pr�fung schreiben.")
        .column("versuch", DataType.INTEGER)
      .build();
  }

}
