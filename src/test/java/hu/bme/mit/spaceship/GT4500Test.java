package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTSp;
  private TorpedoStore mockTSs;

  @BeforeEach
  public void init(){
    mockTSp = mock(TorpedoStore.class, withSettings().useConstructor(10));
    mockTSs = mock(TorpedoStore.class, withSettings().useConstructor(10));
    this.ship = new GT4500(mockTSp, mockTSs);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockTSp.fire(1)).thenReturn(true);
    when(mockTSp.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    //assertEquals(true, result);
    verify(mockTSp, times(1)).fire(1);
    verify(mockTSp, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockTSp.fire(1)).thenReturn(true);
    when(mockTSs.fire(1)).thenReturn(true);
    when(mockTSp.isEmpty()).thenReturn(false);
    when(mockTSs.isEmpty()).thenReturn(false);

    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    //assertEquals(true, result);
    verify(mockTSp, times(1)).fire(1);
    verify(mockTSs, times(1)).fire(1);
    verify(mockTSp, times(1)).isEmpty();
    verify(mockTSs, times(1)).isEmpty();
  }

}
