### Assumptions
1) You cannot add to board the same team, eg. when you add game between Mex - Arg then it is not allowed to add Arg - Mex or Pol - Arg etc. Names of teams are simply checked by String.equals().
2) You cannot change score of game by more than one goal, eg. from 1-0 you can't change to 3-0 or 0-1 etc. There is special implementation of BoardGameScoreModifier which doesn't validate score change. It is used in test.
