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
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
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

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockTSp, times(1)).fire(1);
    verify(mockTSs, times(1)).fire(1);
    verify(mockTSp, times(1)).isEmpty();
    verify(mockTSs, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_Single_fires_one_store(){
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTSp, times(1)).fire(1);
    verify(mockTSs, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Primary_empty_fires_secondary(){
    when(mockTSp.isEmpty()).thenReturn(true);
    
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTSp, times(0)).fire(1);
    verify(mockTSs, times(1)).fire(1);
    verify(mockTSp, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_Is_alternating(){
    
    for (int i = 0; i < 4; i++) {
      ship.fireTorpedo(FiringMode.SINGLE);
    }

    // Assert
    verify(mockTSp, times(2)).fire(1);
    verify(mockTSs, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Primary_twice_on_secondary_empty(){
    when(mockTSs.isEmpty()).thenReturn(true);

    for (int i = 0; i < 2; i++) {
      ship.fireTorpedo(FiringMode.SINGLE);
    }

    // Assert
    verify(mockTSp, times(2)).fire(1);
    verify(mockTSs, times(0)).fire(1);
    verify(mockTSs, times(1)).isEmpty();
  }


  @Test
  public void fireTorpedo_Both_empty(){
    when(mockTSp.isEmpty()).thenReturn(true);
    when(mockTSs.isEmpty()).thenReturn(true);
    
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockTSp, times(1)).isEmpty();
    verify(mockTSs, times(1)).isEmpty();
  }


  // Plan based on solely code
  @Test
  public void fireTorpedo_All_fails_when_one_store_is_empty(){
    when(mockTSp.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

}
