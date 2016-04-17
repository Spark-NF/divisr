package fr.fbb.divisr.canvas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import fr.fbb.divisr.common.IPoint;
import fr.fbb.divisr.common.Point;

public class Canvas implements ICanvas//, ISceneCanvas
{
    private final Point _sceneSize;
    private final Point _screenSize;
    private final SpriteBatch _batch;
    private final ShapeRenderer _shapeRenderer;
    private final Camera _camera;
    private final BitmapFont _font;

    public Canvas(float sceneSize/* TODO: orientation & inner/outer */)
    {
        _sceneSize = new Point(sceneSize, sceneSize);
        _screenSize = new Point();
        _shapeRenderer = new ShapeRenderer();
        _batch = new SpriteBatch();
        _camera = new OrthographicCamera();
        _font = new BitmapFont();
    }

    @Override
    public IPoint getSceneSize()
    {
        return _sceneSize;
    }

    @Override
    public IPoint getScreenSize()
    {
        return _screenSize;
    }

    public void setView(IPoint screenSize)
    {
        _screenSize.setLocation(screenSize);//Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //_tileSize = _screenSize.X() / (_sceneSize.X() + 2 * _offset.X());
        // TODO: adapt to inner/outer
        _sceneSize.y = _sceneSize.X() * _screenSize.Y() / _screenSize.X();
        _camera.viewportWidth = _sceneSize.X();
        _camera.viewportHeight = _sceneSize.Y();
        _camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _shapeRenderer.setProjectionMatrix(_camera.combined);
        Gdx.gl.glLineWidth(2);
        Gdx.gl20.glLineWidth(2);
    }

    @Override
    public void debugGrid(int step)
    {
        Color color = new Color(0.4f, 0.4f, 0.4f, 0.2f);
        for (int j = 0; j <= _sceneSize.Y(); j += step)
            line(color, new Point(0, j), new Point(_sceneSize.X(), j));
        for (int i = 0; i <= _sceneSize.X(); i += step)
            line(color, new Point(i, 0), new Point(i, _sceneSize.Y()));
    }

    @Override
    public void line(Color color, IPoint start, IPoint end)
    {
        _shapeRenderer.setColor(color);
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        _shapeRenderer.line(start.X(), start.Y(), end.X(), end.Y());
        _shapeRenderer.end();
    }

    @Override
    public void line(Color color, IPoint center, IPoint deltaStart, IPoint deltaEnd)
    {
        IPoint start = center.sub(deltaStart);
        IPoint end = center.add(deltaEnd);
        line(color, start, end);
    }

    @Override
    public void rect(Color color, IPoint start, IPoint end)
    {
        preShapeRender(color, ShapeRenderer.ShapeType.Line);
        IPoint delta = start.delta(end);
        _shapeRenderer.rect(start.X(), start.Y(), delta.X(), delta.Y());
        postShapeRender();
    }

    @Override
    public void erect(Color color, IPoint center, IPoint deltaStart, IPoint deltaEnd)
    {
        IPoint start = center.sub(deltaStart);
        IPoint end = center.add(deltaEnd);
        rect(color, start, end);
    }

    @Override
    public void pane(Color color, IPoint start, IPoint end)
    {
        preShapeRender(color, ShapeRenderer.ShapeType.Filled);
        IPoint delta = start.delta(end);
        _shapeRenderer.rect(start.X(), start.Y(), delta.X(), delta.Y());
        postShapeRender();
    }

    @Override
    public void epane(Color color, IPoint center, IPoint deltaStart, IPoint deltaEnd)
    {
        IPoint start = center.sub(deltaStart);
        IPoint end = center.add(deltaEnd);
        rect(color, start, end);
    }

    @Override
    public void circle(Color color, IPoint p, float r)
    {
        // TODO: add segments.
        preShapeRender(color, ShapeRenderer.ShapeType.Line);
        _shapeRenderer.circle(p.X(), p.Y(), r, 40);
        postShapeRender();
    }

    @Override
    public void disk(Color color, IPoint p, float r)
    {
        preShapeRender(color, ShapeRenderer.ShapeType.Filled);
        _shapeRenderer.circle(p.X(), p.Y(), r, 40);
        postShapeRender();
    }

    @Override
    public void text(Color color, String text, IPoint pos, float scale)
    {
        // TODO: Add size
        // TODO: add halign
        _batch.begin();
        _batch.setColor(color);
        //_font.getData().setScale(scale);
        _font.draw(_batch, text, pos.X(), pos.Y());
        _batch.end();
    }

    private void preShapeRender(Color color, ShapeRenderer.ShapeType shapeType)
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        _shapeRenderer.begin(shapeType);
        _shapeRenderer.setColor(color);
    }

    private void postShapeRender()
    {
        _shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /*
     * HELPER FUNCTIONS
     */

    @Override
    public IPoint getScreenPos(IPoint indexPosition)
    {
        final Vector3 pos = _camera.project(new Vector3(indexPosition.X(), indexPosition.Y(), 0));
        return new Point(pos.x, pos.y);
    }

    @Override
    public IPoint getIndexPos(IPoint screenPos)
    {
        final Vector3 pos = _camera.unproject(new Vector3(screenPos.X(), screenPos.Y(), 0));
        return new Point(pos.x, pos.y);
    }

    /*
    @Override
    public IPoint getScreenSize(IPoint indexSize)
    {
        final float x = getScreenSize(indexSize.X());
        final float y = getScreenSize(indexSize.Y());
        return new Point(x, y);
    }

    @Override
    public float getScreenSize(float indexSize)
    {
        return _tileSize * indexSize;
    }
    */
}
