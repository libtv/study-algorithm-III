public class EdgeList implements Comparable<EdgeList> {
    private int xa;
    private int ya;
    private int xb;
    private int yb;
    
    int getYa() {
    	return ya;
    }
    
    public int getYb() {
		// TODO Auto-generated method stub
		return yb;
	}

	public int getXb() {
		// TODO Auto-generated method stub
		return xb;
	}

	int getXa() {
    	return xa;
    }

    public EdgeList(int xa, int ya, int xb, int yb) {
        this.xa = xa;
        this.ya = ya;
        this.xb = xb;
        this.yb = yb;
        
    }

	@Override
	public int compareTo(EdgeList o) {
		int A = o.getYa();
		if (ya > A) return 1;
		else return -1;
	}
}