/* --------------------------------------------------------------------
 * Copyright 2016 Gary W. Lucas.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ---------------------------------------------------------------------
 */

/*
 * -----------------------------------------------------------------------
 *
 * Revision History:
 * Date     Name         Description
 * ------   ---------    -------------------------------------------------
 * 04/2016  G. Lucas     Created
 *
 * Notes:
 *
 * -----------------------------------------------------------------------
 */
package tinfour.test.viewer.backplane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import tinfour.common.IIncrementalTin;
import tinfour.common.IMonitorWithCancellation;
import tinfour.common.IQuadEdge;
import tinfour.common.Vertex;
import tinfour.standard.IncrementalTin;

/**
 * Provides a simple model covering a unit square and containing
 * a relatively small number of points. Intended as a development aid.
 */
public class UnitSquareModel implements IModel {

  List<Vertex> vList;
  IIncrementalTin referenceTin;
  List<Vertex> perimeterList;

  /**
   * Create an instance of the model with the specified number of points.
   *
   * @param count an integer greater than 3.
   */
  public UnitSquareModel(int count) {
    vList = new ArrayList<>();
    vList.add(new Vertex(0, 0, 0, 1));
    vList.add(new Vertex(1, 0, 1, 2));
    vList.add(new Vertex(1, 1, 1, 3));
    vList.add(new Vertex(0, 1, 1, 4));
    Random random = new Random(0);
    for (int i = 5; i <= count; i++) {
      double x = random.nextDouble();
      double y = random.nextDouble();
      double z = x * x + y * y;
      vList.add(new Vertex(x, y, z, i)); // NOPMD
    }
    referenceTin = new IncrementalTin(1.0);
    referenceTin.add(vList, null); // so fast, no monitor is wanted

    List<IQuadEdge> pList = referenceTin.getPerimeter();
    perimeterList = new ArrayList<>();
    for (IQuadEdge e : pList) {
      Vertex a = e.getA();
      double x = a.getX();
      double y = a.getY();
      double z = a.getZ();
      perimeterList.add(new Vertex(x, y, z, -1)); // NOPMD
    }
  }

  @Override
  public int getVertexCount() {
    return vList.size();
  }

  @Override
  public double getMaxX() {
    return 1.0;
  }

  @Override
  public double getMinX() {
    return 0.0;

  }

  @Override
  public double getMaxY() {
    return 1.0;

  }

  @Override
  public double getMinY() {
    return 0;
  }

  @Override
  public double getMaxZ() {
    return 1;
  }

  @Override
  public double getMinZ() {
    return 0;
  }

  @Override
  public double getArea(){
    return 1;
  }

  @Override
  public double getNominalPointSpacing(){
     return Math.sqrt(1 / vList.size() / 0.866);
  }

  @Override
  public void load(IMonitorWithCancellation monitor) throws IOException {
    // No action required
  }

  @Override
  public File getFile() {
    return null;
  }

  @Override
  public String getName() {
    return "Unit Square";
  }

  @Override
  public String getDescription() {
    return "Randomly created vertices";
  }

  @Override
  public List<Vertex> getVertexList() {
    return vList;
  }

  @Override
  public String getFormattedCoordinates(double x, double y) {
    return String.format("%6.3f, %6.3f", x, y);
  }

  @Override
  public String getFormattedX(double x) {
    return String.format("%11.2f", x);
  }

  @Override
  public String getFormattedY(double y) {
    return String.format("%11.2f", y);
  }

  @Override
  public boolean isLoaded() {
    return true;  // loaded by constructor
  }

  @Override
  public IIncrementalTin getReferenceTin() {
    return referenceTin;
  }

  @Override
  public double getReferenceReductionFactor() {
    return 1.0;
  }

  @Override
  public long getTimeToLoadInMillis() {
    return 0;
  }

  @Override
  public long getTimeToSortInMillis() {
    return 0;
  }

  @Override
  public List<Vertex> getPerimeterVertices() {
    return perimeterList;
  }

}