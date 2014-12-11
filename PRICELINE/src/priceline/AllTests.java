package priceline;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.GameBoardMgrTest;
import test.MovesMgrTest;
import test.GameTest;
//import test.GameBoardTest;
import test.GameBoard_10x10_ATest;

@RunWith(Suite.class)
@SuiteClasses({ MovesMgrTest.class, GameBoardMgrTest.class, GameTest.class, GameBoard_10x10_ATest.class})
//@SuiteClasses({ MovesMgrTest.class, GameBoardMgrTest.class, GameTest.class, GameBoard_10x10_ATest.class, GameBoardTest.class})
public class AllTests {

}
