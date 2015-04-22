package com.seitenbau.stu.database.generator.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Optional;
import com.seitenbau.stu.database.generator.Column;
import com.seitenbau.stu.database.generator.Edge;
import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.EntityCreationMode.Direction;
import com.seitenbau.stu.database.generator.values.DataGenerator;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.ValueGenerator;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.constraints.ExpressionConstraint;

public class EntityBlueprint {
	public final Table table; // TODO: private

	private String refName;

	public final LinkedHashMap<String, Object> values; // TODO: private...

	public final Map<String, ArrayList<ConstraintBase>> constraints; // TODO: private...

	private final Map<Edge, EntityCreationMode> relationInformation;

	public Map<Edge, EntityCreationMode> getRelationInformation() {
		return relationInformation;
	}

	private final Map<Edge, List<EntityBlueprint>> referencedBy;

	public Map<Edge, List<EntityBlueprint>> getReferencedBy() {
		return referencedBy;
	}

	// TODO remove again :-)
	private final StringBuilder log;

	EntityBlueprint(Table table, String refName, EntityFactory fab) {
		this.refName = refName;
		this.table = table;
		referencedBy = new HashMap<Edge, List<EntityBlueprint>>();
		values = new LinkedHashMap<String, Object>();
		constraints = new HashMap<String, ArrayList<ConstraintBase>>();
		relationInformation = new HashMap<Edge, EntityCreationMode>();
		log = new StringBuilder();

		for (Column col : table.getColumns()) {
			if (col.getRelation() != null) {
				continue;
			}
			
			ValueGenerator g = fab.getValueGenerator(col);
			if (com.seitenbau.stu.database.generator.values.DataGenerator.class.isInstance(g)) {
				com.seitenbau.stu.database.generator.values.DataGenerator dg = (com.seitenbau.stu.database.generator.values.DataGenerator) g;
				dg.setConstraintsData(fab.model.dataSource);
			}
			
			Result result =  new Result(table, this, col, null, false);	
			result.setGenerator(g);
			values.put(col.getJavaName(), result);
		}

		/*
		 * // TODO: Old remove.... // Generate Constraint instances //table.setColumnConstraints(fab);
		 * 
		 * // for (Column col : table.getColumns()) { // if (col.getRelation() != null) { // continue; // } // // ValueGenerator g = fab.getValueGenerator(col); //
		 * if(DataGenerator.class.isInstance(g)){ // DataGenerator dg = (DataGenerator) g; // dg.setConstraintsData(table.getDataSource()); // } // // // TODO NM if unique flag
		 * (add) is set, ensure value is unique // values.put(col.getJavaName(), g.nextValue()); // }
		 */

	}

	Optional<EntityCreationMode> getCreationInformation(Edge edge) {
		if (relationInformation.containsKey(edge)) {
			return Optional.of(relationInformation.get(edge));
		}

		return Optional.absent();
	}

	void setCreationInformation(Edge edge, EntityCreationMode mode) {
		relationInformation.put(edge, mode);
	}

	public Table getTable() {
		return table;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(table.getJavaName());
		result.append("BP[ ");
		result.append("REF: ");
		result.append(refName);
		result.append(" ");
		for (Entry<String, Object> entry : values.entrySet()) {
			result.append(entry.getKey());
			result.append(":'");
			if (entry.getValue() instanceof EntityBlueprint) {
				EntityBlueprint bp = (EntityBlueprint) entry.getValue();
				result.append(bp.getRefName());
			} else {
				result.append(entry.getValue());
			}
			result.append("' ");
		}
		if (log.length() > 0) {
			result.append("LOG: ");
			result.append(log);
			result.append(" ");
		}
		result.append("]");
		return result.toString();
	}

	public void setValue(String key, Object value) {
		if (value instanceof EntityBlueprint) {
			throw new RuntimeException("Cannot set reference via setValue");
		}
		putValue(key, value);
	}

	public void setReferencing(Edge edge) {
		values.put(edge.getColumn().getJavaName(), null);
	}

	public boolean isReferencing(Edge edge) {
		return values.containsKey(edge.getColumn().getJavaName());
	}

	public void setReference(Edge edge, EntityBlueprint target) {
		putValue(edge.getColumn().getJavaName(), target);
		target.addReferencedBy(edge, this);
	}

	private void putValue(String key, Object value) {
		Object old = values.put(key, value);
		if (old != null && old != value) {
			throw new IllegalStateException("Overwrite " + key + " with " + value + " in " + this + ", existing value: " + old);
		}
	}

	private void addReferencedBy(Edge edge, EntityBlueprint entityBlueprint) {
		List<EntityBlueprint> list = getReferencedByList(edge);
		list.add(entityBlueprint);
	}

	public List<EntityBlueprint> getReferencedByList(Edge edge) {
		List<EntityBlueprint> referencedByList = referencedBy.get(edge);
		if (referencedByList == null) {
			referencedByList = new ArrayList<EntityBlueprint>();
			referencedBy.put(edge, referencedByList);
		}
		return referencedByList;
	}

	public Object getValue(String key) {
		return values.get(key);
	}

	public void appendLog(String text) {
		if (log.length() > 0) {
			log.append(", ");
		}
		log.append(text);
	}
}
