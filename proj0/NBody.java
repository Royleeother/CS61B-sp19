import java.nio.file.Path;
import java.nio.file.Paths;

public class NBody{

    /* readRadius */
    public static double readRadius(String file_name)
    {
        In in = new In(file_name);
        int garbage = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /* readBodies */
    public static Body[] readBodies(String file_name)
    {
        /* Body [][] bd_array ;
        In in         = new In(file_name);
        int M         = in.readInt();   row
        int N         = 6;               column
        double radius = in.readDouble();
        bd_array      = new Body[M][N];  M-by-N array

        for (int i = 0; i < M; i++ )
            for (int j = 0; j < N-1; i++)
                bd_array[i][j] = in.readDouble();
            bd_array[i][N] = in.readString();
        return bd_array; */

        /* switch method !!!
        M-by-N array is not what they demanded
         */
        In in            = new In(file_name);
        int numOfBodies = in.readInt();
        double radius    = in.readDouble();
        Body [] bd_array = new Body[numOfBodies];

        for (int i = 0; i < numOfBodies; i++)
        {
            bd_array[i] = new Body(in.readDouble(), in.readDouble(),
                    in.readDouble(),in.readDouble(),in.readDouble(),
                    in.readString());
        }
        in.close();
        return bd_array;
    }

    public static void main(String[] arg)
    {
        double T  = Double.parseDouble(arg[0]);
        double dt = Double.parseDouble(arg[1]);
        String filename = arg[2];
        /* read in radius and body */
        double radius = readRadius(filename);
        Body[] body = readBodies(filename);

        StdDraw.enableDoubleBuffering();
        /* set the scale */
        double scale = radius + 10;
        StdDraw.setScale(-scale, scale);
        /* image */
        Path p = Paths.get("C:\\Users\\acer\\Desktop\\cs61b\\skeleton-sp19\\proj0\\images\\starfield.jpg");
        String image = p.getFileName().toString();
        StdDraw.picture(0,0, image);

        Body [] bd_array = NBody.readBodies(filename);
        int lengthOfArray = bd_array.length ;
        for (int i = 0; i < lengthOfArray; i++)
        {
            bd_array[i].draw();
        }

        /* represent Time*/
        int time = 0;
        while (time <= T)
        {
            double [] xForces = new double[lengthOfArray];
            double [] yForces = new double[lengthOfArray];
            for (int i = 0; i < lengthOfArray; i++)
            {
                xForces[i] = bd_array[i].calcNetForceExertedByX(bd_array);
                yForces[i] = bd_array[i].calcNetForceExertedByY(bd_array);
            }

            for (int i = 0; i < lengthOfArray; i++)
            {
                bd_array[i].update(dt, xForces[i], yForces[i]);
                bd_array[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(2000);
            time += dt;
        }

        StdOut.printf("%d\n", bd_array.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bd_array.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bd_array[i].xxPos, bd_array[i].yyPos, bd_array[i].xxVel,
                    bd_array[i].yyVel, bd_array[i].mass, bd_array[i].imgFileName);
        }

    }

}