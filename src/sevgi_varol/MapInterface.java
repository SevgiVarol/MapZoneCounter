package sevgi_varol;

public interface MapInterface {
	// Creates / Allocates a map of given size.
	void SetSize(Dimension dim);
	// Get dimensions of given map.
	//This function was not returning Dimension. So I didn't use it thinking it was wrong
	void GetSize(Dimension dim);
	// Sets border at given point.
	void SetBorder(int x, int y) throws Exception;
	// Clears border at given point.
	void ClearBorder(int x, int y) throws Exception;
	// Checks if given point is border.
	boolean IsBorder(int x, int y) throws Exception;
	// Show map contents.
	void Show();
}
