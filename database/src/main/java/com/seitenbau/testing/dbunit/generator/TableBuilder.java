package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.dbunit.generator.Table;

public class TableBuilder
{
  private final String name;

  private Table table;

  public TableBuilder(String name)
  {
    this.name = name;
    this.table = new Table(name);
  }

  public Table build()
  {
    return table;
  }

  public ColumnBuilder column(String name, DataType dataType)
  {
    return new ColumnBuilder(this, name, dataType);
  }
  
  public void addColumn(Column column)
  {
    table.addColumn(column);
  }
}