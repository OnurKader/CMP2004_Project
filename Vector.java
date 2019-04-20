import java.util.Random;

public class Vector {
	public double matrix[][];
	public int rows, cols;
	public double prev_angle = 0.0f, curr_angle = 0.0f;

	public Vector() {
		//	╭───╮
		//	│ 0 │
		//	│ 0 │
		//	╰───╯

		rows = 2;
		cols = 1;
		matrix = new double[rows][cols];
	}

	public Vector(int _temp, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		matrix = new double[this.rows][this.cols];
	}

	public Vector(double x, double y) {
		rows = 2;
		cols = 1;
		matrix = new double[rows][cols];
		matrix[0][0] = x;
		matrix[1][0] = y;
	}

	public Vector(Vector other) {
		this.set(other.matrix);
	}

	public Vector add(Vector other) {
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				this.matrix[y][x] += other.matrix[y][x];
			}
		}
		return this;
	}

	public Vector add(double value) {
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				this.matrix[y][x] += value;
			}
		}
		return this;
	}

	public Vector sub(Vector other) {
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				this.matrix[y][x] -= other.matrix[y][x];
			}
		}
		return this;
	}

	public Vector sub(double value) {
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < cols; x++) {
				this.matrix[y][x] -= value;
			}
		}
		return this;
	}

	public Vector mult(Vector other) {
		Vector result = new Vector(0, this.rows, other.cols);
		if(this.cols == other.rows) {
			for(int y = 0; y < this.rows; y++) {
				for(int x = 0; x < other.cols; x++) {
					for(int k = 0; k < this.cols; k++) {
						result.matrix[y][x] += this.matrix[y][k] * other.matrix[k][x];
					}
				}
			}
		}

		this.matrix = new double[this.rows][other.cols];
		this.cols = other.cols;

		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				this.matrix[y][x] = result.matrix[y][x];
			}
		}
		return this;
	}

	public Vector mult(double scale) {
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				this.matrix[y][x] *= scale;
			}
		}
		return this;
	}

	public void set(double[][] arr) {
		this.matrix = new double[arr.length][arr[0].length];
		this.rows = arr.length;
		this.cols = arr[0].length;
		for(int y = 0; y < arr.length; y++) {
			for(int x = 0; x < arr[y].length; x++) {
				matrix[y][x] = arr[y][x];
			}
		}
	}

	public void Print() {
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				System.out.print("" + this.matrix[y][x] + " ");
			}
			System.out.println();
		}
	}

	@Override
	public String toString() {
		String result = "";
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				result += this.matrix[y][x] + " ";
			}
			result += '\n';
		}
		return result;
	}

	public Vector copy() {
		Vector temp = new Vector(0, this.rows, this.cols);
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				temp.matrix[y][x] = this.matrix[y][x];
			}
		}
		return temp;
	}

	public Vector rotate(double angle) {
		curr_angle = -angle;
		double r_angle = curr_angle - prev_angle;
		Vector transform = new Vector(0, 2, 2);
		double[][] trig = {{Math.cos(r_angle), Math.sin(r_angle)}, {Math.sin(r_angle), -Math.cos(r_angle)}};
		transform.set(trig);

		if(this.rows == 2 && this.cols == 1) {
			transform.mult(this);
			this.matrix = new double[2][1];
			this.matrix[0][0] = transform.matrix[0][0];
			this.matrix[1][0] = -transform.matrix[1][0];
		}
		prev_angle = curr_angle;
		return this.copy();
	}

	public Vector transpose() {
		Vector temp = new Vector(0, this.cols, this.rows);
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				temp.matrix[x][y] = this.matrix[y][x];
			}
		}

		this.matrix = new double[temp.rows][temp.cols];
		this.rows = temp.rows;
		this.cols = temp.cols;

		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				this.matrix[y][x] = temp.matrix[y][x];
			}
		}
		return this;
	}

	public static Vector identity(int size) {
		Vector eye = new Vector(0, size, size);

		for(int y = 0; y < size; y++) {
			eye.matrix[y][y] = 1.0d;
		}

		return eye;
	}

	public double x() {
		return this.matrix[0][0];
	}

	public double y() {
		return -this.matrix[1][0];
	}

	public void x(double a) {
		this.matrix[0][0] = a;
	}

	public void y(double a) {
		this.matrix[1][0] = a;
	}

	public void random() {
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				// From 0 to 1
				this.matrix[y][x] = (new Random()).nextDouble();
			}
		}
	}

	public void random(double end) {
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				// From 0 to end
				this.matrix[y][x] = (new Random()).nextDouble() * end;
			}
		}
	}

	public void random(double start, double end) {
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				// From start to end
				this.matrix[y][x] = start + ((new Random()).nextDouble()) * (end - start);
			}
		}
	}

	public void swap_rows(int i, int j) {
		double[] temp = this.matrix[i];
		this.matrix[i] = this.matrix[j];
		this.matrix[j] = temp;
	}

	public static void swap_2d(double[][] arr, int i, int j) {
		double[] temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void swap_1d(double[] arr, int i, int j) {
		double temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void swap_1d(int arr[], int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void getCofactor(double mat[][], double temp[][], int p, int q, int n)
	{
		int i = 0, j = 0;
		for (int y = 0; y < n; y++)
		{
			for (int x = 0; x < n; x++)
			{
				if (y != p && x != q)
				{
					temp[i][j++] = mat[y][x];
					if (j == n - 1)
					{
						j = 0;
						i++;
					}
				}
			}
		}
	}

	public double determinantOfMatrix(double matr[][], int n)
	{
		double D = 0.0;
		if (n == 1)
			return matr[0][0];
		else if (n == 2) {
			return matr[0][0] * matr[1][1] - matr[0][1] * matr[1][0];
		}
		double temp[][] = new double[this.rows][this.rows];
		int sign = 1;
		for (int i = 0; i < n; i++)
		{
			getCofactor(matr, temp, 0, i, n);
			D += sign * matr[0][i] * determinantOfMatrix(temp, n - 1);
			sign = -sign;
		}
		return D;
}

	public double determinant() {
		if (this.cols != this.rows) {
			System.out.println("Cannot take the determinant of a non-square matrix!");
			return -1;
		} else {
			return determinantOfMatrix(this.matrix, this.rows);
		}
	}

	public static void gaussian(double a[][], int index[])
	{
		int n = index.length;
		double c[] = new double[n];
		for (int i=0; i<n; ++i)
			index[i] = i;
		for (int i=0; i<n; ++i)
		{
			double c1 = 0;
			for (int j=0; j<n; ++j)
			{
				double c0 = Math.abs(a[i][j]);
				if (c0 > c1) c1 = c0;
			}
			c[i] = c1;
		}
		int k = 0;
		for (int j=0; j<n-1; ++j)
		{
			double pi1 = 0;
			for (int i=j; i<n; ++i)
			{
				double pi0 = Math.abs(a[index[i]][j]);
				pi0 /= c[index[i]];
				if (pi0 > pi1)
				{
					pi1 = pi0;
					k = i;
				}
			}
			int itmp = index[j];
			index[j] = index[k];
			index[k] = itmp;
			swap_1d(index, j, k);
			for (int i=j+1; i<n; ++i)
			{
				double pj = a[index[i]][j]/a[index[j]][j];
				a[index[i]][j] = pj;
				for (int l=j+1; l<n; ++l)
					a[index[i]][l] -= pj*a[index[j]][l];
			}
		}
	}

	public static double[][] invert(Vector a)
	{
		int n = a.rows;
		double x[][] = new double[n][n];
		Vector b = Vector.identity(n);
		int index[] = new int[n];

		gaussian(a.matrix, index);

		for (int i=0; i<n-1; ++i)
			for (int j=i+1; j<n; ++j)
				for (int k=0; k<n; ++k)
					b.matrix[index[j]][k] -= a.matrix[index[j]][i]*b.matrix[index[i]][k];
		for (int i=0; i<n; ++i)
		{
			x[n-1][i] = b.matrix[index[n-1]][i]/a.matrix[index[n-1]][n-1];
			for (int j=n-2; j>=0; --j)
			{
				x[j][i] = b.matrix[index[j]][i];
				for (int k=j+1; k<n; ++k)
				{
					x[j][i] -= a.matrix[index[j]][k]*x[k][i];
				}
				x[j][i] /= a.matrix[index[j]][j];
			}
		}
		return x;
	}

	public Vector inverse() {
		if(this.rows == this.cols)
		{
			this.set(invert(this));
		}
		else
		{
			System.out.println("Cannot Invert a Non-Square Matrix");
		}
		return this;
	}

	public Vector solve(Vector b) {
		Vector x = new Vector(0, this.rows, 1);
		x = this.inverse().mult(b);
		return x;
	}

	public void show() {
		System.out.println("x: " + this.x());
		System.out.println("y: " + this.y());
	}



// End of Vector class
}

