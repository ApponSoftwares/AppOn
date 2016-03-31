package com.appon.miniframework;




/**
 * Class used to manage all the request objects  in a queue.
 * Dispatches Request Objects  to particular handlers and response to appropriate
 * contexts. 
 *  
 * @author AppOn
 */
public class EventQueue {
    /** Single instance */
    private static EventQueue instance;
    
    /** Queue to store the process object */
    private Queue requestQueue = new Queue();
    
    private boolean isEnding = false;
    private boolean isOnSelfEndAnim = false;
    private Control hotControl;
    private int currentDelay;
    private boolean isProcsseing = false;
    private LocalizationProvider localProvider;
    private EventQueue()
    {
        
    }
    public String getLocalText(int id)
    {
        if(localProvider != null && id != -1)
        {
            return localProvider.getLocalText(id);
        }
        return null;
    }
    public void registerLocalProvider(LocalizationProvider provider)
    {
        this.localProvider = provider;
    }
    public boolean isProcsseing() {
        return isProcsseing;
    }

    public void setHotControl(Control hotControl) {
        this.hotControl = hotControl;
    }

    public Control getHotControl() {
        return hotControl;
    }
    
    public void reset()
    {
        isEnding = false;
        requestQueue.clear();
        isProcsseing = false;
        ScrollableContainer.isBringingToCenter = false;
    }
    public static EventQueue getInstance() {
        if(instance == null)
        {
            instance = new EventQueue();
        }
        return instance;
    }

    public boolean isEnding() {
        return isEnding;
    }
    
    public void process(Container parent)
    {
       
        if(requestQueue.size() > 0)
        {
            isProcsseing = true;
            if(getHotControl().getDelayInSelection() > 0)
            {
                if(currentDelay++ < getHotControl().getDelayInSelection())
                {
                    return;
                }   
            }
            isEnding = true;
            boolean allOver = allEndingAnimOver(parent);
            while(allOver && requestQueue.size() > 0)
            {
                Event e = (Event)requestQueue.dequeue();
                if(e.getSource() != null && e.getSource().getEventListener() != null)
                {
                   e.getSource().getEventListener().event(e);
                }
            }
        }
       
    }
    public void addEvent(Event e)
    {
        if(e.getSource().isClickable())
        {
            requestQueue.enqueue(e);
            setIsOnSelfEndAnim(e.getSource().isEndAnimOnSelf());
            setHotControl(e.getSource());
            currentDelay = 0;
        }
            
    }

    public boolean isIsOnSelfEndAnim() {
        return isOnSelfEndAnim;
    }

    public void setIsOnSelfEndAnim(boolean isOnSelfEndAnim) {
        this.isOnSelfEndAnim = isOnSelfEndAnim;
    }
    public boolean allStartAnimOver(Container parent)
    {
        boolean allOver = true;
        if(parent.getStartAnimation() != null)
        {
            if(!parent.getStartAnimation().isAnimationOver())
                allOver = false;
        }
        for (int i = 0; i < parent.getSize(); i++) {
            Control c = (Control)parent.getChild(i);
            if(c instanceof Container)
            {
                boolean tmp = allStartAnimOver((Container)c);
                if(!tmp)
                {
                    allOver = false;
                    return allOver;
                }
            }else if(c.getStartAnimation() != null){
                boolean tmp = c.getStartAnimation().isAnimationOver();
                if(!tmp)
                {
                    allOver = false;
                    return allOver;
                }
            }
        }
        return allOver;
    }
    private boolean allEndingAnimOver(Container parent)
    {
        if(isIsOnSelfEndAnim())
        {
             for (int i = 0; i < parent.getSize(); i++) {
                Control c = (Control)parent.getChild(i);
                if(c.getId() == getHotControl().getId())
                {
                    if(c.getEndAnimation() == null)
                    {
                        return true;
                    }
                    return c.getEndAnimation().isAnimationOver();
                }
                if(c instanceof Container)
                {
                    boolean value = allEndingAnimOver((Container)c);
                    if(value)
                    {
                        return true;
                    }
                }
            }
        }
        boolean allOver = true;
        if(parent.getEndAnimation() != null)
        {
            if(!parent.getEndAnimation().isAnimationOver())
                allOver = false;
        }
        for (int i = 0; i < parent.getSize(); i++) {
            Control c = (Control)parent.getChild(i);
            if(c instanceof Container)
            {
                boolean tmp = allEndingAnimOver((Container)c);
                if(!tmp)
                {
                    allOver = false;
                    return allOver;
                }
            }else if(c.getEndAnimation() != null){
                boolean tmp = c.getEndAnimation().isAnimationOver();
                if(!tmp)
                {
                    allOver = false;
                    return allOver;
                }
            }
        }
        return allOver;
    }
   
   
}
