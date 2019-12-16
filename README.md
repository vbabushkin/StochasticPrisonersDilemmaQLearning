# GUI for testing the q-learning algorithm playing the stochastic prisoners dilemma
### Game description
*For more details please refer to [3]*

The stochastic game prisoner’s dilemma (SGPD) [1] is a maze game in which the high-level payoffs of the game equate to a standard prisoner’s dilemma [2]. At the start of each round of the game, the players 1 and 2 are placed in opposite corners of the maze as shown in Figure. The players move (simultaneously) to adjacent cells (up, down, left, or right) with the goal of reaching the other player’s start position in as few moves as possible. Each move costs a player one point, but a player receives 30 points when it reaches its goal. Once both players have arrived at their respective goals, a new round begins from the original start state.

To reach their goals, the players must pass through one of four gates (Gates A-D). While the least-cost path to the goal is through Gate A, only one player can pass through Gate A in a round. When a player passes through Gate A, Gates A, B, and C close for the other player, and it must pass through Gate D. If both players attempt to pass through Gate A at the same time, neither is allowed passage and Gates A and B close. On the other hand, both players can pass through Gates B, C, and D separately or at the same time, though Gate A closes when either player passes through Gate B.

We expect players to eventually converge to one of four solutions, which we list in descending collaborative order:
1. Both B – Both players use Gate B, resulting in each receiving 16 points.
2. Alt A-B – The players take turns going through Gate A, which results in an average payoff of 11 points to each player.
3. Both A – Both players attempt to go through Gate A, thus requiring each player to pass through Gate C. This gives both players 7 points.
4. Bully – One of the players always goes through Gate A, leaving the other to go through Gate D. This gives the players 22 and 0 points, respectively

<img src="https://github.com/vbabushkin/StochasticPrisonersDilemmaQLearningNetBeans/blob/master/pdPicture.png" width="300">
<p><em>Text and figure are replicated from [3], p. 313</em></p>

### References
[1] M. A. Goodrich, J. W. Crandall, and J. R. Stimpson. Neglect tolerant teaming: Issues and dilemmas. In AAAI Spring Symposium on Human Interaction with Autonomous Systems in Complex Environments, 2003.

[2] R. Axelrod. The Evolution of Cooperation. Basic Books, 1984.

[3] Mayada Oudah, Vahan Babushkin, Tennom Chenlinangjia, Jacob Crandall, "Learning to Interact with a Human Partner," 10-th ACM/IEEE International Conference on Human-Robot Interaction, Portland, USA, (2015) http://dl.acm.org/citation.cfm?id=2696482.
