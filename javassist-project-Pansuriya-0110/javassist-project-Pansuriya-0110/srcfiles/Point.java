package target;

public class Point {
   int x, y;

   public void move(Integer dx, Integer dy) {
      x += dx;
      y += dy;
   }

   public void move(int dx, int dy) {
      x += dx;
      y += dy;
   }
   
   public int getX() { return this.x; }
   public int getY() { return this.y; }
}
