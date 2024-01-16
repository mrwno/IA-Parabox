

public class Matrix<E> {
    private int rows;
    private int col;
    private Object element[][];

    public Matrix(int size){
        this.rows = size;
        this.col = size;
        this.element = new Object[rows][col];
    }

    public int getColumnDimension(){
        return this.col;
    }

    public int getRowsDimension(){
        return this.rows;
    }

    public E get(int r, int c){
        E obj = (E) element[r][c];
        return obj;
    }
    
    public void set(int r, int c, E elem){
        element[r][c] = elem;       
    }

    public Matrix<E> clone() {
        Matrix<E> clone = new Matrix<E>(this.rows);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.col; j++) {
                clone.set(i, j, this.get(i, j));
            }
        }
        return clone;
    }
}
