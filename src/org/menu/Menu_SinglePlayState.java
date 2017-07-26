package org.menu;

import javax.microedition.khronos.opengles.*;

import org.framework.*;
import org.framework.openGL.*;
import org.game.*;
import org.menu.MenuState.*;
import org.screen.*;
import org.screen.layer.component.*;

import android.graphics.*;
import android.view.*;

public class Menu_SinglePlayState implements IState {
	
	private Button sItem1Button;
	private Button sItem2Button;
	private Button sItem3Button;
	
	private Button sItemReturnButton;
	
	int i=0;
	
	Paint p = new Paint();
	
	public Menu_SinglePlayState() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		
		sItemReturnButton = new Button(new Point(430, 335), R.drawable.menu_item_return_n, true, true)
				.setVisiable(true)
				.setTouchedBitmap(R.drawable.menu_item_return_t)
				.setFlashEnabled(true)
				.InitNormalSpriteData(R.drawable.menu_item_flash_n, 30, 8, false, true)
				.InitTouchedSpriteData(R.drawable.menu_item_flash_t, 30, 8, false, true);

	}

	@Override
	public void Destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
		switch(MenuState.getAnimationType())
		{
		case OPENING:
			break;
		case NONE:
		{
			sItemReturnButton.Update();
			
			break;
		}
		case CLOSING:
		{
			if(i == 0)
			{
				// 돌아가기 선택
				if(sItemReturnButton.isPushed())
				{
					MenuState.ChangeState(new Menu_MainState());
					MenuState.setAnimationType(AnimationTypes.OPENING);
				}
				else
				{
					AppManager.getInstance().getGameView().ChangeState(new GameState());
					MenuState.setAnimationType(AnimationTypes.OPENING);
				}
			}
						
			break;
		}
		default:
			break;
		
		}
		
	}
	
	@Override
	public void onDrawFrame(GL10 gl, SpriteBatcher spriteBatcher) {
		// TODO Auto-generated method stub
		
		switch(MenuState.getAnimationType())
		{
		case OPENING:
		{
			spriteBatcher.drawBitmap2(R.drawable.singleplaymenu_background, 0, 0, null);
			
			if(i < 255)
			{
				i = i + 30;
				if(i > 255)
					i = 255;
				
				p.setAlpha(i);
				spriteBatcher.drawBitmap2(R.drawable.menu_item_return_n, 430+255/2-i/2, 335, p);
			}
			
			if(i == 255)
			{
				sItemReturnButton.SetReplyTime(System.currentTimeMillis()-3000, 4500);
				MenuState.setAnimationType(AnimationTypes.NONE);
			}
			
			break;
			
		}
		case NONE:
		{
			spriteBatcher.drawBitmap2(R.drawable.singleplaymenu_background, 0, 0, null);

			sItemReturnButton.onDrawFrame(gl, spriteBatcher);
		
			break;
		}
		case CLOSING:
		{
			spriteBatcher.drawBitmap2(R.drawable.singleplaymenu_background, 0, 0, null);
			
			if(i >= 0)
			{
				i = i - 30;
				if(i < 0)
					i = 0;
				
				p.setAlpha(i);
				spriteBatcher.drawBitmap2(R.drawable.menu_item_return_n, 430+255/2-i/2, 335, p);
			}
			
			break;
		}
		default:
			break;
		
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK)
			MenuState.setAnimationType(AnimationTypes.CLOSING);
		
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		switch(MenuState.getAnimationType())
		{
		case OPENING:
			break;
		case NONE:
		{
			if(sItemReturnButton.onTouchEvent(event))
				MenuState.setAnimationType(AnimationTypes.CLOSING);
			
			break;
		}
		case CLOSING:
			break;
		default:
			break;
		}
		
		return false;
	}

}