package fr.fbb.divisr.screens;

public interface Screen
{
	void draw();
	void update(float delta);
	void show();
	void resize(int width, int height);
	void pause();
	void resume();
	void hide();
	void dispose();
}
