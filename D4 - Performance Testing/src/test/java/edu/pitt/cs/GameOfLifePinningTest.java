package edu.pitt.cs;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

public class GameOfLifePinningTest {
  /*
   * READ ME: You may need to write pinning tests for methods from multiple
   * classes, if you decide to refactor methods from multiple classes.
   * 
   * In general, a pinning test doesn't necessarily have to be a unit test; it can
   * be an end-to-end test that spans multiple classes that you slap on quickly
   * for the purposes of refactoring. The end-to-end pinning test is gradually
   * refined into more high quality unit tests. Sometimes this is necessary
   * because writing unit tests itself requires refactoring to make the code more
   * testable (e.g. dependency injection), and you need a temporary end-to-end
   * pinning test to protect the code base meanwhile.
   * 
   * For this deliverable, there is no reason you cannot write unit tests for
   * pinning tests as the dependency injection(s) has already been done for you.
   * You are required to localize each pinning unit test within the tested class
   * as we did for Deliverable 2 (meaning it should not exercise any code from
   * external classes). You will have to use Mockito mock objects to achieve this.
   * 
   * Also, you may have to use behavior verification instead of state verification
   * to test some methods because the state change happens within a mocked
   * external object. Remember that you can use behavior verification only on
   * mocked objects (technically, you can use Mockito.verify on real objects too
   * using something called a Spy, but you wouldn't need to go to that length for
   * this deliverable).
   */

  /* TODO: Declare all variables required for the test fixture. */
  int size = 5; // Small size for simplicity
  MainPanel mainPanel = new MainPanel(size);
  Cell dead, alive;
  Cell[][] cells = new Cell[size][size];

  @Before
  public void setUp() {

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        Cell cell = Mockito.mock(Cell.class);

        if(i >= 1 && i <= 3 && j == 2) {
					Mockito.when(cell.getAlive()).thenReturn(true);
					Mockito.when(cell.toString()).thenReturn("X");
				}
				else {
					Mockito.when(cell.getAlive()).thenReturn(false);
					Mockito.when(cell.toString()).thenReturn(".");
				}

				cells[i][j] = cell;
      }
    }

    mainPanel = new MainPanel(cells);
    dead = new Cell(false);
    alive = new Cell(true);
  }

  @Test
  public void testCalculateNextIteration() {
    int size = 5;

    mainPanel.calculateNextIteration();

    for (int j = 0; j < size; j++) {
      for (int k = 0; k < size; k++) {
        boolean checked = false;
        if (k == 2) {
          if (j == 1 || j == 3) {
            Mockito.verify(cells[j][k]).setAlive(false);
            checked = true;
          } else if (j == 2) {
            Mockito.verify(cells[j][k]).setAlive(true);
            checked = true;
          }
        }

        if (j == 2) {
          if (k == 1 || k == 3) {
            Mockito.verify(cells[j][k]).setAlive(true);
            checked = true;
          }
        }

        if (!checked) {
          Mockito.verify(cells[j][k]).setAlive(false);
        }
        
      }
    }
  }

  @Test
  public void testIterateCell() {
    assertTrue(mainPanel.iterateCell(2, 2));
    assertTrue(mainPanel.iterateCell(2, 1));
    assertTrue(mainPanel.iterateCell(2, 3));
    assertFalse(mainPanel.iterateCell(1, 2));
    assertFalse(mainPanel.iterateCell(3, 2));
  }

  @Test
  public void testToString() {
    assertEquals(".", dead.toString());
    assertEquals("X", alive.toString());
  }

}
