package com.seitenbau.testing.dbunit.model;

import java.util.Date;
import com.seitenbau.testing.util.DateUtil;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class JobsModel
{
  /** actual type : java.lang.Long */
  protected java.lang.Object id;
  /** actual type : java.lang.String */
  protected java.lang.Object title;
  /** actual type : java.lang.String */
  protected java.lang.Object description;

  DatasetIdGenerator _generator;
  public void setIdGenerator(DatasetIdGenerator generator) 
  {
    _generator=generator;
  }

  public JobsModel setId(Integer intValue)
  {
    id = (intValue==null?null:Long.valueOf(intValue));
    return this;
  }
  public JobsModel setId(java.lang.Long value)
  {
    id = value;
    return this;
  }
  public JobsModel setIdRaw(Object value)
  {
    id =  value;
    return this;
  }
  public java.lang.Long getId()
  {
    return (java.lang.Long)  id;
  }
  public java.lang.Object getIdRaw()
  {
    return id;
  }
  public JobsModel nextId()
  {
    Long nextId = _generator.nextId("jobs","id");
    setId(nextId);
    return this;
  }
  public JobsModel setTitle(java.lang.String value)
  {
    title = value;
    return this;
  }
  public JobsModel setTitleRaw(Object value)
  {
    title =  value;
    return this;
  }
  public java.lang.String getTitle()
  {
    return (java.lang.String)  title;
  }
  public java.lang.Object getTitleRaw()
  {
    return title;
  }
  public JobsModel setDescription(java.lang.String value)
  {
    description = value;
    return this;
  }
  public JobsModel setDescriptionRaw(Object value)
  {
    description =  value;
    return this;
  }
  public java.lang.String getDescription()
  {
    return (java.lang.String)  description;
  }
  public java.lang.Object getDescriptionRaw()
  {
    return description;
  }
 
}
