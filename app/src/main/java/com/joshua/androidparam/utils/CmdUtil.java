package com.joshua.androidparam.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class CmdUtil {
	
	/**
	 * @return
	 */
	public static boolean isRoot() {
		try {
			String result = adb("date");
			if (!TextUtils.isEmpty(result)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param path
	 * @return
	 */
	public static boolean deleteDataFile(String path) {
		try {
			String cmd = "rm -rf " + path;
			adb(cmd);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @param
	 */
	public static String[] lsDataDir(String path){
		return dataLs("ls ", path);	
	}
	public static String[] lsLDataDir(String path){
		return dataLs("ls -l ", path);
	}
	/**
	 * ls 
	 * @param command
	 * @param path
	 * @return
	 */
	public static String[] dataLs(String command,String path){
		String cmd = command + path;
		try {
			String result = CmdUtil.adb(cmd);
			if(result.equals("")){
				return null;
			}
			String lsResult[] = result.split("\n");
			String[] resultArr = new String[lsResult.length];
			for(int i = 0;i<lsResult.length;i++){
				if(!path.endsWith("/")){
					resultArr[i]=path+"/"+lsResult[i];
				}else resultArr[i]=path+lsResult[i];
			}
			return resultArr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 /**
	  * 
	  * @param packageName
	  * @return
	  */
	public static boolean forceStopApp(String packageName) {  
    	String cmd = "am force-stop " + packageName + " \n";  
		try {
			SuCommand(cmd);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
    } 
	
	 /** 
     * packageɾ
     */  
	public static void ClearApk(String packageName) {  
    	String cmd = "pm clear " + packageName + " \n"; 
    	SuCommand(cmd);
    } 
//	localStringBuffer.append("pm clear " + str).append("\n");

	/**
	 * 
	 * @param packageName
	 * @return
	 */
	public static boolean unInstallApk(String packageName) {
		String cmd = "pm uninstall " + packageName;
		try {
			String res = CmdUtil.adb(cmd);
			if(res.contains("Success")){
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static String installApkWithResult(String filePath) {
		String cmd = "pm install -r " + filePath;
		try {
			String res = CmdUtil.adb(cmd);
			return res;
		} catch (Exception e) {
		}
		return "";
	}
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean installApk(String filePath) {
		String cmd = "pm install -r " + filePath;
		try {
			String res = CmdUtil.adb(cmd);
			if(res.contains("Success")){
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	/**
	 * 
	 * @param context
	 * @param fullPackageName
	 * @param fullActivityName
	 */
	public static void openApk(Context context,String fullPackageName,String fullActivityName){
		String cmd = "am start -n " + fullPackageName+"/"+fullActivityName;
		try {
			CmdUtil.SuCommand(cmd);
		} catch (Exception e) {
		} 
	}
	/**
	 * getpid
	 * @param packageName
	 * @return
	 */
	public static int getPid(String packageName) {
        BufferedReader br = null;
        DataOutputStream dos = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(process.getOutputStream());
            dos.writeBytes("pidof " + packageName + "\n");
            dos.writeBytes("exit \n");
            dos.flush();
            process.waitFor();
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String tmp = null;
            if((tmp = br.readLine()) != null) {
                int pid = Integer.parseInt(tmp);
                return pid;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(dos, br);
            if (process != null) {
                process.destroy();
            }
        }
        return -1;
    }
 
	/************************************************************************************/
    /**
     * @param cmd
     */
    public static void SuCommand(String cmd) {
        DataOutputStream dos = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");//new ProcessBuilder("sr").start();
            dos = new DataOutputStream(process.getOutputStream());
            dos.writeBytes(cmd + "\n");
            dos.writeBytes("exit \n");
            dos.flush();
            int exit = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(dos);
            process.destroy();
        }
    }

    /**
     * 命令返回
     *
     * @param cmd
     * @return
     */
    public static String adb(String cmd) {
        DataOutputStream dos = null;
        Process process = null;
        DataInputStream din = null;
        try {
            process = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(process.getOutputStream());
//            for (Object cmd:cmds){
            dos.writeBytes(cmd.toString() + "\n");
//            }
            dos.writeBytes("exit \n");
            dos.flush();
            int exit =  process.waitFor();
            din = new DataInputStream(process.getInputStream());
            StringBuffer sb = new StringBuffer();
            byte[] buffer = new byte[1024];
            int tmp = -1;
            while ((tmp = din.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, tmp));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(dos, din);
            if (process != null) {
                process.destroy();
            }
        }
        return null;
    }
    
	/**
	 * 定时执行
	 * @param cmds
	 * @param time
	 * @return
	 */
	public static boolean runCmdWithTime(final String[] cmds,long time){
        if(time==-1){
            time = 60000;
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<Boolean> futureTask =
                new FutureTask<Boolean>(new Callable<Boolean>() {
                    public Boolean call() {
                        //真正的任务，这里的返回值类型为String，可以为任意类型
//                        String cmds[] ={"pkill dex2oat",
//
//
//                                "tar " + sTarParm + " -czvf /data/local/tmp/" + FinalFileName + " " + RemoteFilePath};
                        SuCommandList(cmds);
                        return true;
                    }});
        executor.execute(futureTask);
        //在这里可以做别的任何事情
        Boolean result = false;
        try {
            //取得结果，同时设置超时执行时间为0.1秒。同样可以用future.get()，不设置执行超时时间取得结果
            result = futureTask.get(time, TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            futureTask.cancel(true);
        } catch (ExecutionException e) {
            futureTask.cancel(true);
        } catch (TimeoutException e) {
            futureTask.cancel(true);
            //超时后，进行相应处理
        } finally {
            executor.shutdown();
        }
        return  result;
    }
	/**
     * @param cmds
     */
    public static void SuCommandList(String[] cmds) {
        DataOutputStream dos = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");// process = new ProcessBuilder("sr").start();
            dos = new DataOutputStream(process.getOutputStream());
            for (String cmd : cmds) {
                dos.writeBytes(cmd + "\n");
            }
//            dos.flush();
            dos.writeBytes("exit \n");
            dos.flush();
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(dos);
            process.destroy();
        }
    }

    public static void SuCommandList(List cmds) {
        if (cmds == null || cmds.size() == 0) {
            return;
        }
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            for (Object obj : cmds) {
                os.writeBytes(obj.toString() + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
    }
    
    
    
    /**
     * @param closeables
     */
    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.toString();
                }

            }
        }

    }
}
