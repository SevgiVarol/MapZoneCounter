package sevgi_varol;

import java.util.ArrayList;

public class MapZoneCounter implements ZoneCounterInterface, MapInterface {
	MapInterface map;
	int[][] matrix;
	Dimension dim;
	int[][] labels;
	ArrayList<Integer> linked = new ArrayList();

	public MapZoneCounter() {
		this.map = this;
	}

	public static void main(String[] args) {
		MapZoneCounter counter = new MapZoneCounter();
		try {
			counter.Init(counter.map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Init(MapInterface map) throws Exception {
		//This information is the dimention and edge information of the representative map.
		//In case of interface modifications, it can be created directly by the user.
		dim = new Dimension(24, 36);
		map.SetSize(dim);
		map.SetBorder(13, 0); map.SetBorder(13, 1); map.SetBorder(12, 2); map.SetBorder(11, 3); map.SetBorder(10, 4); map.SetBorder(10, 5);
		map.SetBorder(9, 5); map.SetBorder(9, 6); map.SetBorder(8, 7); map.SetBorder(7, 8); map.SetBorder(7, 9); map.SetBorder(6, 10); 
		map.SetBorder(5, 11); map.SetBorder(4, 12); map.SetBorder(4, 13); map.SetBorder(3, 14); map.SetBorder(2, 15); map.SetBorder(1, 16);
		map.SetBorder(1, 17); map.SetBorder(0, 18); map.SetBorder(11, 6); map.SetBorder(12, 7); map.SetBorder(13, 7); map.SetBorder(14, 8);
		map.SetBorder(15, 8); map.SetBorder(16, 9); map.SetBorder(17, 9); map.SetBorder(17, 10); map.SetBorder(18, 10); map.SetBorder(19, 10);
		map.SetBorder(20, 11); map.SetBorder(21, 11); map.SetBorder(22, 12); map.SetBorder(23, 12); map.SetBorder(17, 11); map.SetBorder(17, 12);
		map.SetBorder(16, 13); map.SetBorder(16, 14); map.SetBorder(16, 15); map.SetBorder(16, 16); map.SetBorder(15, 17);
		map.SetBorder(15, 18); map.SetBorder(15, 19); map.SetBorder(15, 20); map.SetBorder(14, 21); map.SetBorder(14, 22);
		map.SetBorder(14, 23); map.SetBorder(14, 24); map.SetBorder(13, 25); map.SetBorder(13, 26); map.SetBorder(13, 27);
		map.SetBorder(13, 28); map.SetBorder(13, 29); map.SetBorder(14, 29); map.SetBorder(15, 29); map.SetBorder(16, 30);
		map.SetBorder(17, 30); map.SetBorder(18, 31); map.SetBorder(19, 31); map.SetBorder(20, 31); map.SetBorder(21, 32);
		map.SetBorder(22, 32); map.SetBorder(23, 33); map.SetBorder(12, 28); map.SetBorder(11, 28); map.SetBorder(10, 27);
		map.SetBorder(9, 27); map.SetBorder(8, 27); map.SetBorder(7, 26); map.SetBorder(6, 26); map.SetBorder(5, 26);
		map.SetBorder(4, 25); map.SetBorder(3, 25); map.SetBorder(2, 24); map.SetBorder(1, 24); map.SetBorder(0, 24);
		map.SetBorder(5, 35); map.SetBorder(6, 34); map.SetBorder(6, 33); map.SetBorder(6, 32); map.SetBorder(6, 31);
		map.SetBorder(6, 30); map.SetBorder(6, 29); map.SetBorder(6, 28); map.SetBorder(6, 27);

		Solve();
	}

	@Override
	public int Solve() throws Exception {
		TwoPassAlgorithm();
		Show();
		System.out.println("zone count=" + linked.size());
		return linked.size();
	}

	@Override
	public void SetSize(Dimension dim) {
		matrix = new int[dim.width][dim.height];
		/*
		 * map.dimention = dim 
		 * MapInterface must contain the dimention variable. SetSize
		 * method should set this dimention and GetSize method should return this
		 * dimention
		 */

	}

	@Override
	public void GetSize(Dimension dim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void SetBorder(int x, int y) throws Exception {
		if (dim.CheckWithin(x, y)) {
			matrix[x][y] = 1;
		}

	}

	@Override
	public void ClearBorder(int x, int y) throws Exception {
		if (IsBorder(x, y)) {
			matrix[x][y] = 0;
		}

	}

	@Override
	public boolean IsBorder(int x, int y) throws Exception {
		if (matrix[x][y] == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void Show() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(labels[i][j] + " ");
			}
			System.out.println();
		}

	}

	public ArrayList<CornerObject> findNeighbour(CornerObject obj) {
		ArrayList<CornerObject> neighbourList = new ArrayList<CornerObject>();
		if (obj.colomn > 0 && matrix[obj.row][obj.colomn - 1] == 0) {
			neighbourList.add(new CornerObject(obj.row, obj.colomn - 1));
		}
		if (obj.row > 0 && matrix[obj.row - 1][obj.colomn] == 0) {
			neighbourList.add(new CornerObject(obj.row - 1, obj.colomn));
		}
		if (obj.row < dim.width - 1 && matrix[obj.row + 1][obj.colomn] == 0) {
			neighbourList.add(new CornerObject(obj.row + 1, obj.colomn));
		}
		if (obj.colomn < dim.height - 1 && matrix[obj.row][obj.colomn + 1] == 0) {
			neighbourList.add(new CornerObject(obj.row, obj.colomn + 1));
		}

		return neighbourList;
	}

	public void TwoPassAlgorithm() {

		labels = new int[dim.width][dim.height];
		int zoneCount = 1;

		// First pass

		for (int i = 0; i < dim.width; i++) {
			for (int j = 0; j < dim.height; j++) {
				if (matrix[i][j] == 0) {
					ArrayList<CornerObject> neighbors = findNeighbour(new CornerObject(i, j));
					if (neighbors.isEmpty()) {
						labels[i][j] = zoneCount;
						zoneCount++;

					} else {

						int min = getMinLabel(neighbors);
						if (min == 0) {
							min = zoneCount;
							zoneCount++;
						}

						if (labels[i][j] == 0) {
							labels[i][j] = min;
						}

						for (int m = 0; m < neighbors.size(); m++) {
							CornerObject item = neighbors.get(m);
							labels[item.row][item.colomn] = labels[i][j];
						}

					}
				}
			}
		}

		// Second pass
		for (int i = 0; i < dim.width; i++) {
			for (int j = dim.height - 1; j > 0; j--) {
				if (matrix[i][j] == 0) {
					ArrayList<CornerObject> neighbors = findNeighbour(new CornerObject(i, j));
					if (!neighbors.isEmpty()) {
						int min = getMinLabel(neighbors);
						if (min != 0) {
							labels[i][j] = min;
							for (int m = 0; m < neighbors.size(); m++) {
								CornerObject item = neighbors.get(m);
								labels[item.row][item.colomn] = labels[i][j];
							}
							if (!linked.contains(min)) {
								linked.add(min);
							}

						}

					}
				}

			}

		}

	}

	public int getMinLabel(ArrayList<CornerObject> neighbors) {
		ArrayList<Integer> neighbourLabels = new ArrayList();
		for (int l = 0; l < neighbors.size(); l++) {
			int neighbourLabel = labels[neighbors.get(l).row][neighbors.get(l).colomn];
			if (neighbourLabel != 0) {
				neighbourLabels.add(neighbourLabel);
			}

		}
		int min = 0;
		if (!neighbourLabels.isEmpty()) {
			min = neighbourLabels.get(0);
			for (int m = 0; m < neighbourLabels.size(); m++) {
				if (neighbourLabels.get(m) < min) {
					min = neighbourLabels.get(m);

				}
			}
		}
		return min;
	}

}
