package sevgi_varol;

public interface ZoneCounterInterface {
	// Feeds map data into solution class, then get ready for Solve() method.
	void Init(MapInterface map) throws Exception;
	// Counts zones in map provided with Init() method, then return the result.
	int Solve() throws Exception;
}
