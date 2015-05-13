package com.seitenbau.stu.database.generator.values.constraints.logical;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.hints.GreaterEqualHint;
import com.seitenbau.stu.database.generator.hints.GreaterHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.SmallerHint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.CompareConstraint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.constraints.Source;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class SmallerConstraint extends CompareConstraint {

	public SmallerConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	public SmallerConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	public SmallerConstraint(Value<?> value, String sourceName) {
		super(value, sourceName);
	}

	@Override
	public boolean isValid(EntityBlueprint eb) {

		try {
			if (sourceNames.length == 2)
				return getSources().get(0).getResults().get(0).getValue().compareTo(getSources().get(1).getResults().get(0).getValue()) < 0;

			return getSources().get(0).getResults().get(0).getValue().compareTo(getValue(0)) < 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public ConstraintBase getCopyInstance() {
		SmallerConstraint eqConstraint;
		if (sourceNames.length == 2)
			eqConstraint = new SmallerConstraint(sourceNames[0], sourceNames[1]);
		else
			eqConstraint = new SmallerConstraint(getValue(0), sourceNames[0]);

		eqConstraint.fab = this.fab;

		return eqConstraint;
	}

	@Override
	public ArrayList<Hint> getHint(Result result) {
		ArrayList<Hint> hints = new ArrayList<Hint>();
		
		if (this.getValue(0) != null) {
			SmallerHint hint = new SmallerHint(this);
			hint.setValue(this.getValue(0));
			hints.add(hint);
		} else {
			for (Source source : sources) {
				for (Result r : source.getResults()) {
					if (r != result) {
						if (r.isGenerated()) {
							SmallerHint hint = new SmallerHint(this);
							hint.setValue(r.getValue());
							hints.add(hint);
							return hints;
						}
					}
				}
			}
		}

		return hints;
	}

}