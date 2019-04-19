// Will Convert Some Processing Functions to built-in Java ones, like min(), import math and other utils as well

public class Matrix {
  float mat[][];
  int rows, cols;
  float prev_angle, curr_angle;

  //Default Constructor sets the Matrix to be 2x2 set to zero
  Matrix() {
    prev_angle = 0;
    curr_angle = 0;
    rows = 2;
    cols = 2;
    mat = new float[rows][cols];
    for (int y=0; y < rows; y++) {
      for (int x=0; x < cols; x++) {
        mat[y][x] = 0;
      }
    }
  }

  //Create a ROWxCOL Matrix and initialize to be zero
  Matrix(int row, int col) {
    prev_angle = 0.0;
    curr_angle = 0.0;
    rows = row;
    cols = col;
    mat = new float[rows][cols];
    for (int y=0; y < rows; y++) {
      for (int x=0; x < cols; x++) {
        mat[y][x] = 0;
      }
    }
  }

  //Adds the other Matrix's element at every index
  Matrix add(Matrix other) {
    for (int y=0; y < rows; y++) {
      for (int x=0; x < cols; x++) {
        this.mat[y][x] += other.mat[y][x];
      }
    }
    return this;
  }
  
  Matrix add(float val) {
    for (int y=0; y < rows; y++) {
      for (int x=0; x < cols; x++) {
        this.mat[y][x] += val;
      }
    }
    return this;
  }
  
  Matrix sub(Matrix other) {
    for (int y=0; y < rows; y++) {
      for (int x=0; x < cols; x++) {
        this.mat[y][x] -= other.mat[y][x];
      }
    }
    return this;
  }
  
  Matrix sub(float val) {
    for (int y=0; y < rows; y++) {
      for (int x=0; x < cols; x++) {
        this.mat[y][x] -= val;
      }
    }
    return this;
  }
  
  //Parametric Multiplication
  Matrix mult(Matrix other) {
    Matrix result = new Matrix(this.rows, other.cols);
    if (other.rows == this.cols) {
      for (int y=0; y < this.rows; y++) {
        for (int x=0; x < other.cols; x++) {
          for (int k=0; k < this.cols; k++) {
            result.mat[y][x] += this.mat[y][k] * other.mat[k][x];
          }
        }
      }
      this.mat = new float[this.rows][other.cols];
      this.cols = other.cols;

      for (int y=0; y < this.rows; y++) {
        for (int x=0; x < other.cols; x++) {
          this.mat[y][x] = result.mat[y][x];
        }
      }
    } else {
      println("Wrong Size! Matrix hasn't changed!");
    }
    return this;
  }

  Matrix mult(float scale) {
    for (int y=0; y < this.rows; y++)
      for (int x=0; x < this.cols; x++)
        this.mat[y][x] *= scale;
    return this;
  }


  //Prints in a readable manner
  void Print() {
    for (int y=0; y < this.rows; y++) {
      for (int x=0; x < this.cols; x++) {
        print(this.mat[y][x] + "  ");
      }
      print("\n");
    }
  }

  PVector Rotate(float angle) {
    PVector result = new PVector();
    curr_angle = angle;
    float r_angle = curr_angle - prev_angle;
    Matrix transform = new Matrix(2, 2);
    float[][] tri = {{cos(r_angle), sin(r_angle)}, {sin(r_angle), -cos(r_angle)}};
    transform.set(tri); 
    if (this.cols == 1 && this.rows == 2) {
      transform.mult(this);
      this.mat = new float[2][1];
      this.mat[0][0] = transform.mat[0][0];
      this.mat[1][0] = -transform.mat[1][0]; 
      result = new PVector(this.mat[0][0], this.mat[1][0]);
    } else {
      println("Cannot rotate a non-vector Matrix");
    }
    prev_angle = curr_angle;
    return result;
  }

  //Copy Constructor
  Matrix(Matrix other) {
    this.set(other.mat);
  }


  Matrix transpose() {
    Matrix temp = new Matrix(this.cols, this.rows);
    for (int y=0; y < this.rows; y++) {
      for (int x=0; x < this.cols; x++) {
        temp.mat[x][y] = this.mat[y][x];
      }
    }

    this.mat = new float[temp.rows][temp.cols];
    this.rows = temp.rows;
    this.cols = temp.cols;

    for (int y=0; y < this.rows; y++) {
      for (int x=0; x < this.cols; x++) {
        this.mat[y][x] = temp.mat[y][x];
      }
    }
    return this;
  }

  //Finds the smallest number of columns and rows and sets the Matrix to be MINxMIN identity
  void identity() {
    int size = min(this.cols, this.rows);
    Matrix id = new Matrix(size, size);

    for (int i=0; i < size; i++) 
      id.mat[i][i] = 1;

    this.mat = new float[size][size];
    this.rows = size;
    this.cols = size;
    for (int y=0; y < this.rows; y++) {
      for (int x=0; x < this.cols; x++) {
        this.mat[y][x] = id.mat[y][x];
      }
    }
  }

  Matrix set(float[][] input) {
    int row = input.length;
    int col = input[0].length;
    int i;
    int error[] = new int[row];   
    float temp[] = new float[input[0].length];
    this.mat = new float[row][col];
    //Check if all columns are filled
    //  3  5
    // -2 -1
    //  1  x     if x is null, create and make zero
    for (i=0; i < error.length; i++) {
      if (input[i].length != col) {
        for (int j=0; j < input[i].length; j++) {
          temp[j] = input[i][j];
          input[i] = new float[i];
          input[i] = temp;
        }
      }
    }

    this.rows = row;
    this.cols = col;
    for (int y=0; y < row; y++)
      for (int x=0; x < col; x++)
        this.mat[y][x] = input[y][x];

    //Negate the y value which is row[1] col[0]
    /* if (this.cols == 1 && this.rows == 2) //If it's a vector
     this.mat[1][0] *= -1;
     */
    return this;
  }

  Matrix set(float val) {
    for (int y=0; y < this.rows; y++)
      for (int x=0; x < this.cols; x++)
        this.mat[y][x] = val;
    return this;
  }

  Matrix set(Matrix other) {
    this.mat = new float[other.rows][other.cols];
    this.rows = other.rows;
    this.cols = other.cols;
    for (int y=0; y < this.rows; y++)
      for (int x=0; x < this.cols; x++)
        this.mat[y][x] = other.mat[y][x];
    return this;
  }

  float x() {
    return this.mat[0][0];
  }

  float y() {
    return -this.mat[1][0];
  }

  void x(float a){
  this.mat[0][0] = a;
  }
  
  void y(float a){
  this.mat[1][0] = a;
  }

  void swap_rows(int i, int j) {
    float[] temp = this.mat[i]; 
    this.mat[i] = this.mat[j];
    this.mat[j] = temp;
  }

  void swap_2d(float arr[][], int i, int j) {
    float[] temp = arr[i]; 
    arr[i] = arr[j];
    arr[j] = temp;
  }

  void swap_1d(float arr[], int i, int j) {
    float temp = arr[i]; 
    arr[i] = arr[j];
    arr[j] = temp;
  }

  void swap_1d(int arr[], int i, int j) {
    int temp = arr[i]; 
    arr[i] = arr[j];
    arr[j] = temp;
  }

  void Random() {
    for (int y=0; y < this.rows; y++) {
      for (int x=0; x < this.cols; x++) {
        this.mat[y][x] = random(1.0);
      }
    }
  }

  void Random(float end) {
    for (int y=0; y < this.rows; y++) {
      for (int x=0; x < this.cols; x++) {
        this.mat[y][x] = random(end);
      }
    }
  }

  void Random(float begin, float end) {
    for (int y=0; y < this.rows; y++) {
      for (int x=0; x < this.cols; x++) {
        this.mat[y][x] = random(begin, end);
      }
    }
  }

  void Floor() {
    for (int y=0; y < this.rows; y++) {
      for (int x=0; x < this.cols; x++) {
        this.mat[y][x] = floor(this.mat[y][x]);
      }
    }
  }

  void getCofactor(float mat[][], float temp[][], int p, int q, int n) 
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

  float determinantOfMatrix(float matr[][], int n) 
  { 
    float D = 0.0;
    if (n == 1)
      return matr[0][0];
    else if (n == 2) {
      return matr[0][0] * matr[1][1] - matr[0][1] * matr[1][0];
    }

    float temp[][] = new float[this.rows][this.rows];
    int sign = 1;

    for (int i = 0; i < n; i++) 
    {
      getCofactor(matr, temp, 0, i, n);
      D += sign * matr[0][i] * determinantOfMatrix(temp, n - 1);
      sign = -sign;
    }
    return D;
  }

  float determinant() {
    if (this.cols != this.rows) {
      println("Cannot take the determinant of a non-square matrix!"); 
      return -1;
    } else {
      return determinantOfMatrix(this.mat, this.rows);
    }
  }

  void gaussian(float a[][], int index[])
  {
    int n = index.length;

    float c[] = new float[n];
    for (int i=0; i<n; ++i)
      index[i] = i;
    for (int i=0; i<n; ++i)
    {
      float c1 = 0;
      for (int j=0; j<n; ++j) 
      {
        float c0 = Math.abs(a[i][j]);
        if (c0 > c1) c1 = c0;
      }
      c[i] = c1;
    }
    int k = 0;
    for (int j=0; j<n-1; ++j) 
    {
      float pi1 = 0;
      for (int i=j; i<n; ++i) 
      {
        float pi0 = abs(a[index[i]][j]);
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
        float pj = a[index[i]][j]/a[index[j]][j];
        a[index[i]][j] = pj;
        for (int l=j+1; l<n; ++l)
          a[index[i]][l] -= pj*a[index[j]][l];
      }
    }
  }

  float[][] invert(Matrix a)
  {
    int n = a.rows;
    float x[][] = new float[n][n];
    Matrix b = new Matrix(n, n);
    b.identity();
    int index[] = new int[n];

    for (int i=0; i<n; ++i) 
      b.mat[i][i] = 1;

    gaussian(a.mat, index);

    for (int i=0; i<n-1; ++i)
      for (int j=i+1; j<n; ++j)
        for (int k=0; k<n; ++k)
          b.mat[index[j]][k] -= a.mat[index[j]][i]*b.mat[index[i]][k];

    for (int i=0; i<n; ++i)
    {
      x[n-1][i] = b.mat[index[n-1]][i]/a.mat[index[n-1]][n-1];
      for (int j=n-2; j>=0; --j) 
      {
        x[j][i] = b.mat[index[j]][i];
        for (int k=j+1; k<n; ++k) 
        {
          x[j][i] -= a.mat[index[j]][k]*x[k][i];
        }
        x[j][i] /= a.mat[index[j]][j];
      }
    }
    return x;
  }

  Matrix inverse() {
    if (this.rows == this.cols) {
      this.set(invert(this));
    } else println("Cannot invert a non-square Matrix!");
    return this;
  }

  Matrix solve(Matrix b) {
    Matrix x = new Matrix(this.rows, 1);
    //A * x = b
    //x = A^-1 * b
    //x = a.inverse().mult(b);
    x = this.inverse().mult(b);
    return x;
  }

  void show() {
    println("x : " + this.x()); 
    println("y : " + this.y());
  }
}
