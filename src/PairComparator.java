import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;


public class PairComparator implements Comparator<SimpleEntry<?, Integer>> {

	@Override
	public int compare(SimpleEntry<?, Integer> arg0,
			SimpleEntry<?, Integer> arg1) {
		return arg0.getValue()-arg1.getValue();
	}

}
