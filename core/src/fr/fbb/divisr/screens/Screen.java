package fr.fbb.divisr.screens;

public interface Screen
{
	void show();
	void draw();
	void update(float delta);
	void resize(int width, int height);
	void pause();
	void resume();
	void hide();
	void dispose();
}
