package folderol;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class Actions {

	static class jumpToIngamemenu extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public jumpToIngamemenu(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("ESC -> Ingamemenu");
			houston.changeAppearance(false, false, "INGAMEMENU");
		}

	}

	static class jumpToGame extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public jumpToGame(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("ESC -> Game");
			houston.changeAppearance(false, true, "GAME");
		}

	}

	static class resetPlayer extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public resetPlayer(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("pressedUp");
			houston.player.resetPosition();
		}

	}
	
	static class moveUp extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public moveUp(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("pressedUp");
			houston.player.setYMovement(-1);
		}

	}

	static class moveDown extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public moveDown(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("pressedDown");
			houston.player.setYMovement(1);
		}

	}

	static class moveLeft extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public moveLeft(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("pressedLeft");
			houston.player.setXMovement(-1);
		}

	}

	static class moveRight extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public moveRight(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("pressedRight");
			houston.player.setXMovement(1);
		}

	}

	static class releasedUp extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public releasedUp(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("releasedUp");
			houston.player.setYMovement(0);
		}

	}

	static class releasedDown extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public releasedDown(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("releasedDown");
			houston.player.setYMovement(0);
		}

	}

	static class releasedLeft extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public releasedLeft(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("releasedLeft");
			houston.player.setXMovement(0);
		}

	}

	static class releasedRight extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private Houston houston;

		public releasedRight(Houston houston) {
			this.houston = houston;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("releasedRight");
			houston.player.setXMovement(0);
		}

	}

}
